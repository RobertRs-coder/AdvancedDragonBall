package com.advanced.advanceddragonball.data.local.datastore

interface PrefsDataStore {

    suspend fun saveToken(key: String, value: String)
    suspend fun getToken(key: String): String?
    suspend fun deleteToken(key: String)

}