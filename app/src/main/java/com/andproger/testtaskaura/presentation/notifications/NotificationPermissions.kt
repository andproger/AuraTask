package com.andproger.testtaskaura.presentation.notifications

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

fun Context.requestNotificationPermission(
    requestPermissionLauncher: ActivityResultLauncher<String>,
    onPermissionGranted: () -> Unit = {}
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED -> {
                onPermissionGranted()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    } else {
        onPermissionGranted()
    }
}