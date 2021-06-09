package ru.hsHelper.androidApp.ui.settings.observers

import kotlinx.coroutines.runBlocking

abstract class SettingsObserver : AutoCloseable {
    override fun close() {
        if (changed) {
            runBlocking {
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

