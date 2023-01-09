package com.advanced.advanceddragonball.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore (name = "user_datastore")

class DataStoreImpl @Inject constructor(
    @ApplicationContext context: Context)
    : com.advanced.advanceddragonball.datastore.DataStore {

    private val settingsDataStore = context.dataStore

    companion object {

        val NAME = stringPreferencesKey("NAME")
        val PHONE_NUMBER = stringPreferencesKey("PHONE_NUMBER")
        val ADDRESS = stringPreferencesKey("ADDRESS")

    }

    override suspend fun saveData(value: String, item: String) {
        settingsDataStore.edit { preferences ->
            preferences[stringPreferencesKey(item)] = value
        }
    }

    override suspend fun readData(item: String): String {
        val dataStoreKey = stringPreferencesKey(item)
        val preferences = settingsDataStore.data.first()
        return preferences[dataStoreKey].toString()
    }
}