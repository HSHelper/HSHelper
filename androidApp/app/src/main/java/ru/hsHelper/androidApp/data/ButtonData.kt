package ru.hsHelper.androidApp.data

import android.view.View

data class ButtonData(
    val mainText: String,
    val listener: (View) -> Unit
)
