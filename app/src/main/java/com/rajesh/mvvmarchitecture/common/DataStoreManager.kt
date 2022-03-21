package com.rajesh.mvvmarchitecture.common

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject


/**
 * Wrapper class for store key value pairs
 * @author Rajesh Koshti
 */

private val Context.dataStore by preferencesDataStore(Constant.PREFERENCES_NAME)

class DataStoreManager @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore

    suspend fun putString(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    suspend fun getString(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    suspend fun putInt(key: String, value: Int) {
        val dataStoreKey = intPreferencesKey(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    suspend fun getInt(key: String): Int? {
        val dataStoreKey = intPreferencesKey(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    suspend fun putDouble(key: String, value: Double) {
        val dataStoreKey = doublePreferencesKey(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    suspend fun getDouble(key: String): Double? {
        val dataStoreKey = doublePreferencesKey(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    suspend fun putFloat(key: String, value: Float) {
        val dataStoreKey = floatPreferencesKey(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    suspend fun getFloat(key: String): Float? {
        val dataStoreKey = floatPreferencesKey(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    // Example which return flow
    /*suspend fun setThemeMode(mode: Int) {
        settingsDataStore.edit { settings ->
            settings[Settings.NIGHT_MODE] = mode
        }
    }

    val themeMode: Flow<Int> = settingsDataStore.data.map { preferences ->
        preferences[Settings.NIGHT_MODE] ?: AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
    }*/
}