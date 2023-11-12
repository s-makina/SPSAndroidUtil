package com.sps.sps_android_util.update_handler

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun UpdateRequiredView(update: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "New Update Available",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W700)
        )

        Text(
            text = "Update to proceed to use the app",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W700)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(modifier = Modifier,
            onClick = { update() }) {
            Text(
                text = "Update",
            )
        }
    }
}