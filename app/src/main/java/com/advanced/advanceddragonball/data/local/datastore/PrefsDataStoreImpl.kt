package com.advanced.advanceddragonball.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_datastore")

class PrefsDataStoreImpl @Inject constructor(
    private val context: Context
) : PrefsDataStore {

    override suspend fun saveToken(key: String, value: String) {
        val preferenceKey = stringPreferencesKey(key)
        context.dataStore.edit {
            it[preferenceKey] = value
        }
    }

    override suspend fun getToken(key: String): String? {
        return runCatching {
            val preferenceKey = stringPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[preferenceKey]
        }.getOrNull()
    }

    override suspend fun deleteToken(key: String) {
        val preferenceKey = stringPreferencesKey(key)
        context.dataStore.edit {
            if (it.contains(preferenceKey)) {
                it.remove(preferenceKey)
            }
        }
    }
}