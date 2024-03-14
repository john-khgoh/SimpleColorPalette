package com.example.simplecolorpalette

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    private val TAG = "UserPreferencesRepository"
    private companion object {
        val FONT_COLOR = stringPreferencesKey("fontColor")
    }

    //Function to save preference to Data Store
    suspend fun savePreferences(fontColor: String) {
        dataStore.edit { preferences ->
            preferences[FONT_COLOR] = fontColor
        }
    }

    //Read preference from Data Store
    val fontColor: Flow<String> = dataStore.data
        .catch {
            if(it is IOException) {
                Log.e(TAG,"Error reading preferences.",it)
            } else {
                throw it
            }
        }
        .map {
            preferences -> preferences[FONT_COLOR] ?: "#000000"
        }
}


