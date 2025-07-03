package com.pi.ProjectInclusion.database

import android.content.Context
import android.content.SharedPreferences
import com.pi.ProjectInclusion.constants.ConstantVariables.SHARED_PREF_KEY

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
}