package com.sps.spsandroidutil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sps.sps_android_util.dialog.Alert
import com.sps.sps_android_util.dialog.AlertType
import com.sps.spsandroidutil.ui.theme.SPSAndroidUtilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SPSAndroidUtilTheme {
                // A surface container using the  'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val errorAlert = remember { mutableStateOf(false) }
                    val successAlert = remember { mutableStateOf(false) }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = { errorAlert.value = true }) {
                            Text(text = "Show Error Alert")
                        }
                        Button(onClick = { successAlert.value = true }) {
                            Text(text = "Show Success Alert")
                        }
                    }

                    Alert(
                        state = errorAlert, alertType = AlertType.Error(
                            title = "Error",
                            content = "An Error have occurred, please try again later"
                        )
                    )
                    Alert(
                        state = successAlert, alertType = AlertType.Success(
                            "Success",
                            content = "Your Request have been processed successfully"
                        )
                    )
                }
            }
        }
    }
}