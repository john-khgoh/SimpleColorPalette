package com.example.simplecolorpalette

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore


private const val FONT_COLOR = "#000000"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = FONT_COLOR
)
class ColorPaletteApplication: Application() {

    lateinit var userPreferencesRepository: UserPreferencesRepository
    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}