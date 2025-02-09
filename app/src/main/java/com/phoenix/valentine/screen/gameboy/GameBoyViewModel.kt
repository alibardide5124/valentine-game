package com.phoenix.valentine.screen.gameboy

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GameBoyViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(GameBoyUiState())
    val uiState = _uiState.asStateFlow()

    private val offset = 0.01f

    fun onEvent(event: GameBoyUiEvent) {
        when (event) {
            is GameBoyUiEvent.UpdateCharacterPosition -> {
                when(event.positionChange) {
                    PositionChange.LEFT -> {
                        _uiState.update {
                            if (isInBound(it.characterPositionX, it.characterBoundX.start..1f))
                                it.copy(characterPositionX = it.characterPositionX - offset)
                            else it
                        }
                    }
                    PositionChange.RIGHT ->
                        _uiState.update {
                            if (isInBound(it.characterPositionX, 0f..it.characterBoundX.endInclusive))
                                it.copy(characterPositionX = it.characterPositionX + offset)
                            else it
                        }
                    PositionChange.UP -> {
                        _uiState.update {
                            if (isInBound(it.characterPositionY, it.characterBoundY.start..1f))
                                it.copy(characterPositionY = it.characterPositionY - offset)
                            else it
                        }
                    }
                    PositionChange.DOWN -> {
                        _uiState.update {
                            if (isInBound(it.characterPositionY, 0f..it.characterBoundY.endInclusive))
                                it.copy(characterPositionY = it.characterPositionY + offset)
                            else it
                        }
                    }
                }
            }

            is GameBoyUiEvent.OnClickActionButton -> {}
            GameBoyUiEvent.OnClickStartSelect -> {}
        }
    }
}

private fun isInBound(offset: Float, bounds: ClosedFloatingPointRange<Float>): Boolean {
    return offset in bounds
}