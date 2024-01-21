package com.example.midtermproject.domain.feature_auth.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository{
    suspend fun saveSession(key: Preferences.Key<Boolean>, value: Boolean)

    fun readSession(key: Preferences.Key<Boolean>) : Flow<Boolean>

    suspend fun clear()
}