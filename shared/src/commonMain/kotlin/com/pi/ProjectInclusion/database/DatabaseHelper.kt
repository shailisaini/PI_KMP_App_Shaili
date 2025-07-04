package com.pi.ProjectInclusion.database

import com.pi.ProjectInclusiondatabase.SELECT_LANGUAGE

class DatabaseHelper(driverFactory: DatabaseDriverFactory) {
    private val database = PiDatabase(driverFactory.createDriver())
    private val queries = database.piDatabaseQueries // Based on .sq file name

    fun insertLanguage(langId: Long, icon: String, text: String) {
        queries.insertLanguages(langId, icon, text)
    }

    fun getAllLanguages(): List<SELECT_LANGUAGE> {
        return queries.getAllLanguage().executeAsList()
    }

    fun clearLanguages() {
        queries.deleteAllLanguages()
    }
}