package com.example.midtermproject.data.auth_feature.local.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.midtermproject.domain.feature_auth.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(private val dataStore : DataStore<Preferences>) :
    DataStoreRepository {

    companion object {
        val SESSION_KEY = booleanPreferencesKey("user_session_key")
    }

    override suspend fun saveSession(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    override fun readSession(key: Preferences.Key<Boolean>): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[key] ?: false
        }
    }

    override suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}
