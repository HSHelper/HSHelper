package ru.hsHelper.androidApp.ui.settings.observers

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class SettingsObserver : AutoCloseable {
    override fun close() {
        if (changed) {
            GlobalScope.launch {
                send()
            }
        }
    }

    private var changed = false

    protected fun markChanged(vararg ignored: Any?): Boolean {
        changed = true
        return true
    }

    protected abstract suspend fun send()
}

