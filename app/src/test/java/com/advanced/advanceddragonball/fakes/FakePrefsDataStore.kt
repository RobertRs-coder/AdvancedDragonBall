package com.advanced.advanceddragonball.fakes

import com.advanced.advanceddragonball.data.local.datastore.PrefsDataStore

class FakePrefsDataStore: PrefsDataStore {
    override suspend fun saveToken(key: String, value: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getToken(key: String): String? {
        return "Testing"
    }

    override suspend fun deleteToken(key: String) {
        TODO("Not yet implemented")
    }
}