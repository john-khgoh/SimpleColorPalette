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
    private val _uiState = MutableStateFlow(UiState2())
    val uiState2: StateFlow<UiState2> = _uiState.asStateFlow()

    val uiState: StateFlow<UiState> = userPreferencesRepository.fontColor.map{ fontColorValue ->
        UiState(fontColor = mutableStateOf(fontColorValue))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UiState()
    )

    //Store the font Color
    fun saveFontColor(fontColor: String) {
        viewModelScope.launch {
            userPreferencesRepository.savePreferences(fontColor)
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
    //var selectedColor: MutableState<String> = mutableStateOf("#000000"),
    //var fontColor: MutableState<String> = mutableStateOf("#000000"),
    var fontColor: MutableState<String> = mutableStateOf("#000000"),
)
data class UiState2(
    var openColorDialog: MutableState<Boolean> = mutableStateOf(false)
)
