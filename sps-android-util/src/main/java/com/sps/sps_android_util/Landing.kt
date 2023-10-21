package com.sps.sps_android_util

import android.content.res.Resources
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun Alert(
    state: MutableState<Boolean> = mutableStateOf(true),
    alertType: AlertType,
    onClose: () -> Unit = {}
) {
    if (state.value) {
        Dialog(onDismissRequest = { /*TODO*/ }) {
            Surface(
                modifier = Modifier.fillMaxWidth(1f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = alertType.getContainerColor()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = alertType.icon),
                                contentDescription = null,
                                modifier = Modifier.size(60.dp),
                                tint = alertType.getContentColor()
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = alertType.title, style = MaterialTheme.typography.titleLarge)
                        Text(
                            text = alertType.content,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )

                    }
                    Button(
                        onClick = {
                            onClose()
                            state.value = false
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = alertType.getContainerColor(),
                            contentColor = alertType.getContentColor()
                        )
                    ) {
                        Text(text = "Close")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

sealed class AlertType(
    open val title: String = "Success",
    open val content: String = "Your",
    open val icon: Int = R.drawable.ic_error,
    open val contentColor: Color? = null,
    open val containerColor: Color? = null
) {
    data class Success(
        override val title: String = "Success",
        override val content: String = "Your",
        override val icon: Int = R.drawable.ic_success,
        override val contentColor: Color? = Color(0xFFFFFFFF),
        override val containerColor: Color? = Color(0xFF22c55e)
    ) : AlertType()

    data class Error(
        override val title: String = "Error",
        override val content: String = "Your",
        override val icon: Int = R.drawable.ic_error,
        override val contentColor: Color? = Color(0xFFFFFFFF),
        override val containerColor: Color? = Color(0xFFf87171)
    ) : AlertType()
}

@Composable
fun AlertType.getContainerColor(): Color {
    return getHarmonizedColor(containerColor ?: MaterialTheme.colorScheme.errorContainer)
}

@Composable
fun AlertType.getContentColor(): Color {
    return getHarmonizedColor(contentColor ?: MaterialTheme.colorScheme.onErrorContainer)
}

@Composable
fun getHarmonizedColor(color: Color): Color {
    val originalColor = MaterialTheme.colorScheme.primary
    // Adjust the alpha of the original color
    val harmonizedColor = Color(
        red = originalColor.red,
        green = originalColor.green,
        blue = originalColor.blue,
        alpha = 0.5f // Adjust the alpha value as needed
    )
    return color
// Blend the theme color and the adjusted original color
//    return Color(
//        red = (harmonizedColor.red + color.red) / 2,
//        green = (harmonizedColor.green + color.green) / 2,
//        blue = (harmonizedColor.blue + color.blue) / 2
//    )
}