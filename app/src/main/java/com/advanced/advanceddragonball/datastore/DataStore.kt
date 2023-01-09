package com.advanced.advanceddragonball.datastore

interface DataStore {
    suspend fun saveData(value: String, item: String)

    suspend fun  readData(item: String): String
}