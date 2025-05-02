package com.example.data.local

import android.content.Context
import android.content.SharedPreferences
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import androidx.core.content.edit
import kotlinx.serialization.serializer

class SharedPreferenceManager(context: Context) {
    val appPrefs: SharedPreferences by lazy {
        context.getSharedPreferences(PREF_DEFAULT, Context.MODE_PRIVATE)
    }

    fun put(key: String, value: Any?) =
        appPrefs.edit(commit = true) {
            run {
                when (value) {
                    is String -> putString(key, value)
                    is Boolean -> putBoolean(key, value)
                    is Int -> putInt(key, value)
                    is Long -> putLong(key, value)
                    is Float -> putFloat(key, value)
                }
            }
        }


    /**
     * 객체 리스트를 SharedPreferences에 저장한다..
     */
    inline fun <reified T> saveObjectList(key: String, list: List<T>) {
        val json = Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
        }
        val elementSerializer: KSerializer<T> = serializer()
        val listSerializer = ListSerializer(elementSerializer)
        val jsonString = json.encodeToString(listSerializer, list)
        put(key, jsonString)
    }

    /**
     * SharedPreferences에서 객체 리스트를 불러온다.
     */
    inline fun <reified T> getObjectList(key: String): List<T> {
        val json = Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
        }
        val jsonString = appPrefs.getString(key, null)
        val elementSerializer: KSerializer<T> = serializer()
        val listSerializer = ListSerializer(elementSerializer)

        return jsonString?.let {
            json.decodeFromString(listSerializer, it)
        } ?: emptyList()
    }

    companion object {
        const val PREF_DEFAULT = "PREF_DEFAULT"
    }
}