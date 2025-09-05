package com.pi.ProjectInclusion.database

interface LocalDataSource {
    fun saveValue(key: String, value: String)
    fun getValue(key: String, defaultValue: String = ""): String
    fun clearValue(key: String)
    fun clearAll()
}