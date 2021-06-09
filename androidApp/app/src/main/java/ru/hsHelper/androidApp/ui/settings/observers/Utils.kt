package ru.hsHelper.androidApp.ui.settings.observers

import androidx.annotation.StringRes
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

fun <T : Preference> find(preference: PreferenceFragmentCompat, @StringRes id: Int): T =
    preference.findPreference(preference.requireContext().resources.getString(id))!!
