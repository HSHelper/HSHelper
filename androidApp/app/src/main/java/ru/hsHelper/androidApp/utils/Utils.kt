package ru.hsHelper.androidApp.utils

import android.app.Activity
import android.util.DisplayMetrics
import android.os.Build

fun getCurrentWindowWidth(activity: Activity) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        activity.windowManager.currentWindowMetrics.bounds.width()
    } else {
        val metrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        metrics.widthPixels
    }
