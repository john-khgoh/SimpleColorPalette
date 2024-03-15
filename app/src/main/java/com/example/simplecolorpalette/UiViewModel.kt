package com.example.simplecolorpalette

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class UiViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    //Get the font color
    val getFontColor: Flow<String> = userPreferencesRepository.fontColor

    //Set the font Color
    fun saveFontColor(fontColor: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveFontColorPreferences(fontColor)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ColorPaletteApplication)
                UiViewModel(application.userPreferencesRepository)
            }
        }
    }
}

data class UiState(
    var openColorDialog: MutableState<Boolean> = mutableStateOf(false),
    var fontColor: MutableState<String> = mutableStateOf("#000000")
)
