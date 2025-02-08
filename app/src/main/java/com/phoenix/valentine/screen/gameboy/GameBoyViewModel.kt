package com.phoenix.valentine.screen.gameboy

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GameBoyViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(GameBoyUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: GameBoyUiEvent) {
        // TODO: Implement Events
    }

}