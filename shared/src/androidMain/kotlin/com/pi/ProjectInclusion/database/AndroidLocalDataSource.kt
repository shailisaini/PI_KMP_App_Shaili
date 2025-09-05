package com.pi.ProjectInclusion.database

import android.content.Context
import android.content.SharedPreferences
import com.pi.ProjectInclusion.constants.ConstantVariables.SHARED_PREF_KEY
import androidx.core.content.edit

class AndroidLocalDataSource(
    context: Context
) : LocalDataSource {

    private val prefs: SharedPreferences = context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)

    override fun saveValue(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    override fun getValue(key: String, defaultValue: String): String {
        return prefs.getString(key, defaultValue) ?: defaultValue
    }

    override fun clearValue(key: String) {
        prefs.edit { remove(key) }
    }

    override fun clearAll() {
        prefs.edit { clear() }
    }

}