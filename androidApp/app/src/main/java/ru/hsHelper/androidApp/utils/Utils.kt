package ru.hsHelper.androidApp.utils

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

fun getCurrentWindowWidth(activity: Activity) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        activity.windowManager.currentWindowMetrics.bounds.width()
    } else {
        val metrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        metrics.widthPixels
    }

fun ComponentActivity.withPermissions(permissions: Array<String>, action: () -> Unit) {
    val name = this.componentName.className
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if (!it.values.contains(false)) {
                Log.i(name, "got permissions")
                action()
            } else {
                Log.i(name, "permissions not granted")
            }
        }

    var isGranted = true
    for (permission in permissions) {
        if (ContextCompat.checkSelfPermission(this, permission)
            != PackageManager.PERMISSION_GRANTED
        ) {
            isGranted = false
        }
    }

    if (isGranted) {
        Log.i(name, "got permissions")
        action()
    } else {
        Log.i(name, "ask permissions")
        requestPermissionLauncher.launch(permissions)
    }
}
