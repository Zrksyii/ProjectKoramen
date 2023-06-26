package com.example.projectkoramen.db

import android.content.Context
import com.example.projectkoramen.data.SettingModel

internal class SettingPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "setting_pref"
        private const val NAME = "name"
        private const val PHONE_NUMBER = "phone"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    fun setSetting(value: SettingModel) {
        val editor = preferences.edit()
        editor.putString(NAME, value.name)
        editor.putString(PHONE_NUMBER, value.phoneNumber)
        editor.apply()
    }

    fun getSetting(): SettingModel {
        val model = SettingModel()
        model.name = preferences.getString(NAME, "")
        model.phoneNumber = preferences.getString(PHONE_NUMBER, "")
        return model
    }
}