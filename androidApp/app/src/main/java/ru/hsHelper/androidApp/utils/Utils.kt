package ru.hsHelper.androidApp.utils

import android.app.Activity
import android.util.DisplayMetrics

fun getCurrentWindowWidth(activity: Activity) =
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
        activity.windowManager.currentWindowMetrics.bounds.width()
    } else {
        val metrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        metrics.widthPixels
    }
