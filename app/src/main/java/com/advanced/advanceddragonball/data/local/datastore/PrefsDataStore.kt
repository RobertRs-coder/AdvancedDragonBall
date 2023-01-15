package com.advanced.advanceddragonball.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

interface PrefsDataStore {

    suspend fun saveToken(key: String, value: String)
    suspend fun getToken(key: String): String?
    suspend fun deleteToken(key: String)

}