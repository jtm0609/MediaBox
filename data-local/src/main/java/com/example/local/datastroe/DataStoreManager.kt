package com.example.local.datastroe

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

@Singleton
class DataStoreManager @Inject constructor(
    context: Context
) {
    private val dataStore = context.dataStore

    private fun createPreferencesKey(key: String) = stringPreferencesKey(key)

    suspend fun putString(key: String, value: String) {
        val prefKey = createPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[prefKey] = value
        }
    }

    suspend inline fun <reified T> putObjectList(key: String, list: List<T>) {
        val json = Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
        }
        val elementSerializer: KSerializer<T> = serializer()
        val listSerializer = ListSerializer(elementSerializer)
        val jsonString = json.encodeToString(listSerializer, list)
        putString(key, jsonString)
    }

    fun getStringFlow(key: String, defaultValue: String = ""): Flow<String> {
        val prefKey = createPreferencesKey(key)
        return dataStore.data.map { preferences ->
            preferences[prefKey] ?: defaultValue
        }
    }

    inline fun <reified T> getObjectListFlow(key: String): Flow<List<T>> {
        val json = Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
        }
        val elementSerializer: KSerializer<T> = serializer()
        val listSerializer = ListSerializer(elementSerializer)

        return getStringFlow(key).map { jsonString ->
            if (jsonString.isNotEmpty()) {
                json.decodeFromString(listSerializer, jsonString)
            } else {
                emptyList()
            }
        }
    }
}