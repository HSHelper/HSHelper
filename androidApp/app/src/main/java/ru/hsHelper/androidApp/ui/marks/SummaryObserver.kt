package ru.hsHelper.androidApp.ui.marks

import android.widget.TextView
import androidx.lifecycle.Observer

class SummaryObserver(private val textView: TextView) : Observer<MarkInterval?> {
    override fun onChanged(data: MarkInterval?) {
        if (data != null) {
            textView.text = data.toString()
        } else {
            textView.text = ""
            textView.height = 0
        }
    }
}
