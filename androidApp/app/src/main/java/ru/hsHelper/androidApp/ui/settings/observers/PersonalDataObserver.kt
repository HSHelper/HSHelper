package ru.hsHelper.androidApp.ui.settings.observers

import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import kotlinx.coroutines.runBlocking
import ru.hsHelper.R
import ru.hsHelper.androidApp.auth.AuthProvider
import ru.hsHelper.androidApp.auth.getRestUser
import ru.hsHelper.androidApp.rest.RestProvider
import ru.hsHelper.androidApp.rest.codegen.models.UserUpdateRequest

class PersonalDataObserver(preference: PreferenceFragmentCompat) : SettingsObserver() {
    private val firstName = find<EditTextPreference>(preference, R.string.first_name)
    private val secondName = find<EditTextPreference>(preference, R.string.second_name)

    init {
        runBlocking {
            val user = AuthProvider.currentUser!!.getRestUser()
            firstName.text = user.firstName
            secondName.text = user.lastName
            firstName.setOnBindEditTextListener(::markChanged)
            secondName.setOnBindEditTextListener(::markChanged)
        }
    }

    override suspend fun send() {
        val user = AuthProvider.currentUser!!.getRestUser()
        RestProvider.userApi.updateUserUsingPUT(
            user.id,
            UserUpdateRequest(
                user.email,
                firstName.text,
                secondName.text,
                user.firebaseMessagingToken!!
            )
        )
    }
}
