package ru.hsHelper.androidApp.ui.settings

import androidx.annotation.StringRes
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

abstract class SettingsObserver : AutoCloseable {
    companion object {
        @JvmStatic
        protected fun <T : Preference> find(
            preference: PreferenceFragmentCompat,
            @StringRes id: Int
        ): T {
            return preference.findPreference(
                preference.requireContext().resources.getString(id)
            )!!
        }
    }

    private var changed = false

    protected fun markChanged(any: Any?) {
        changed = true
    }

    override fun close() {
        if (changed) {
            send()
        }
    }

    protected abstract fun send()
}

