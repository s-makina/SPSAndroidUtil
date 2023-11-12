package com.sps.sps_android_util.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.UUID

const val mask = "xx xxx xx xx"

fun log(content: Any?) {
    Log.i("BETH", "$content")
}

fun toast(context: Context, message: String?) {
    Toast.makeText(context, "$message", Toast.LENGTH_LONG).show()
}

fun getUTCTimeStamp(): String {
    val tz: TimeZone = TimeZone.getTimeZone("UTC")
    val df: DateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'", Locale.getDefault()) // Quoted "Z" to indicate UTC, no timezone offset
    df.timeZone = tz
    return df.format(Date())
}

fun getCurrentDate(): String{
    val date = Date(System.currentTimeMillis())
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return format.format(date)
}

fun readableDate(date: String): String {
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val formatter = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
        formatter.format(parser.parse(date)).toString()
    } catch (e: Exception) {
        log("${e.message}")
        date
    }
}

fun getShortDate(date: String): String {
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        formatter.format(parser.parse(date)).toString()
    } catch (e: Exception) {
        log("${e.message}")
        date
    }
}

fun getUUID(): String {
    return UUID.randomUUID().toString()
}

fun Double?.toMoneyFormat(): String {
    return try {
        val formatter: NumberFormat = DecimalFormat("#,###.##")
        "MK "+formatter.format(this)
    } catch (e: Exception) {
        log(e.message)
        "MK 0.00"
    }
}

fun Double.to2Decimal(): Double{
    return String.format("%.2f", this).toDouble()
}

fun mobileNumberFilter(text: AnnotatedString): TransformedText {
    // change the length
    val trimmed =
        if (text.text.length >= 12) text.text.substring(0..8) else text.text

    val annotatedString = AnnotatedString.Builder().run {
        for (i in trimmed.indices) {
            append(trimmed[i])
            if (i == 1 || i == 4 || i == 6) {
                append(" ")
            }
        }
        pushStyle(SpanStyle(color = Color.LightGray))
        append(mask.takeLast(mask.length - length))
        toAnnotatedString()
    }

    val phoneNumberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {

            if (offset <= 1) return offset
            if (offset <= 4) return offset + 1
            if (offset <= 6) return offset + 2
            if (offset <= 9) return offset + 3

            return 12
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 1) return offset
            if (offset <= 4) return offset - 1
            if (offset <= 6) return offset - 2
            if (offset <= 9) return offset - 3
            return 9
        }
    }
    return TransformedText(annotatedString, phoneNumberOffsetTranslator)
}


fun Int?.toNumberFormat(): String {
    return try {
        val formatter: NumberFormat = DecimalFormat("#,###")
        formatter.format(this)
    } catch (e: Exception) {
        log(e.message)
        "0"
    }
}

fun generateOTP(): String {
    val randomPin = (Math.random() * 9000).toInt() + 1000
    return randomPin.toString()
}

inline fun <reified T> parseObject(data: JsonObject, typeToken: Type): T {
    val gson = GsonBuilder().create()
    return gson.fromJson(data, typeToken)
}

inline fun <reified T> parseObject(data: Any, typeToken: Type): T {
    val json: String = Gson().toJson(data)
    val gson = GsonBuilder().create()
    return gson.fromJson(json, typeToken)
}

inline fun <reified T> fromJson(json: String, typeToken: Type): T {
    val gson = GsonBuilder().create()
    return gson.fromJson(json, typeToken)
}

internal fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}