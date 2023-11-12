package com.sps.sps_android_util.update_handler

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun UpdateInProgress(progress: Float) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Update in progress",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W700)
        )

        Text(
            text = "We are downloading your ",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W700)
        )

        if (!progress.isNaN()) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(progress = progress)
        }
    }
}