package com.sps.sps_android_util.update_handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.google.android.play.core.ktx.AppUpdateResult
import com.sps.sps_android_util.util.findActivity
import se.warting.inappupdate.compose.rememberInAppUpdateState
import kotlinx.coroutines.launch

private const val APP_UPDATE_REQUEST_CODE = 86500

@Composable
fun UpdateChecker(content: @Composable () -> Unit) {
    val inAppUpdateState = rememberInAppUpdateState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    when (val state = inAppUpdateState.appUpdateResult) {
        AppUpdateResult.NotAvailable -> content()
        is AppUpdateResult.Available -> {
            UpdateRequiredView {
                state.startImmediateUpdate(context.findActivity(), APP_UPDATE_REQUEST_CODE)
            }
        }
        is AppUpdateResult.InProgress -> {
            UpdateInProgress(
                progress = state.installState.bytesDownloaded()
                    .toFloat() / state.installState.totalBytesToDownload().toFloat()
            )
        }
        is AppUpdateResult.Downloaded -> {
            UpdateDownloadedView {
                scope.launch {
                    state.completeUpdate()
                }
            }
        }
    }
}