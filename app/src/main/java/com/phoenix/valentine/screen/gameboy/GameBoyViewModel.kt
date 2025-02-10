package com.phoenix.valentine.screen.gameboy

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phoenix.valentine.model.CharacterDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameBoyViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(GameBoyUiState())
    val uiState = _uiState.asStateFlow()

    private val POSITION_OFFSET = 0.01f

    fun onEvent(event: GameBoyUiEvent) {
        when (event) {
            is GameBoyUiEvent.UpdateCharacterPosition -> {
                if (uiState.value.actionState == ActionState.NONE)
                    handleCharacterPosition(event.positionChange)
            }

            is GameBoyUiEvent.OnClickActionButton -> {
                handleActionButtonClick(event.actionButton)
            }

            GameBoyUiEvent.OnClickStartSelect -> {
                _uiState.update { it.copy(displayCredit = true) }
            }
        }
    }

    private fun handleCharacterPosition(
        positionChange: PositionChange
    ) {
        _uiState.update { currentState ->
            val newPosition = when (positionChange) {
                PositionChange.LEFT -> currentState.characterPositionX - POSITION_OFFSET
                PositionChange.RIGHT -> currentState.characterPositionX + POSITION_OFFSET
                PositionChange.UP -> currentState.characterPositionY - POSITION_OFFSET
                PositionChange.DOWN -> currentState.characterPositionY + POSITION_OFFSET
            }
            // Change Character direction
            _uiState.update {
                it.copy(
                    characterDirection =
                    when (positionChange) {
                        PositionChange.LEFT ->  CharacterDirection.LEFT
                        PositionChange.RIGHT -> CharacterDirection.RIGHT
                        PositionChange.UP -> CharacterDirection.UP
                        PositionChange.DOWN -> CharacterDirection.DOWN
                    }
                )
            }
            // Use the Rect to check if the new position is in bounds
            if (isWithinBounds(positionChange, newPosition)) {
                when (positionChange) {
                    PositionChange.LEFT -> currentState.copy(characterPositionX = newPosition)
                    PositionChange.RIGHT -> currentState.copy(characterPositionX = newPosition)
                    PositionChange.UP -> currentState.copy(characterPositionY = newPosition)
                    PositionChange.DOWN -> currentState.copy(characterPositionY = newPosition)
                }
            } else {
                currentState
            }
        }
    }

    private fun handleActionButtonClick(actionButton: ActionButton) {
        when (actionButton) {
            ActionButton.A -> {
                if (isYesActionWithinBounds()) {
                    _uiState.update { it.copy(actionState = ActionState.YES) }
                }
                if (isNoActionWithinBounds()) {
                    viewModelScope.launch {
                        _uiState.update { it.copy(displayHole = true) }
                        delay(500)
                        _uiState.update { it.copy(displayCharacter = false) }
                        delay(500)
                        _uiState.update {
                            it.copy(
                                actionState = ActionState.NO,
                                noAttempts = it.noAttempts + 1,
                            )
                        }
                        delay(3500)
                        _uiState.update {
                            it.copy(
                                displayHole = false,
                                displayCharacter = true,
                                characterPositionX = 0.5f,
                                characterPositionY = 0.8f,
                                actionState = ActionState.NONE,
                                characterBounds = if (it.noAttempts < 5) it.characterBounds
                                else it.characterBounds.copy(right = 0.68f)
                            )
                        }
                    }
                }
            }

            ActionButton.B -> {
                _uiState.update { it.copy(displayCredit = false) }
            }
        }
    }

    private fun isWithinBounds(positionChange: PositionChange, newPosition: Float): Boolean {
        return with(uiState.value) {
            when (positionChange) {
                PositionChange.LEFT, PositionChange.RIGHT ->
                    characterBounds.left <= newPosition && newPosition <= characterBounds.right

                PositionChange.UP, PositionChange.DOWN ->
                    characterBounds.top <= newPosition && newPosition <= characterBounds.bottom
            }
        }
    }

    private fun isYesActionWithinBounds(): Boolean {
        return with(uiState.value) {
            yesBounds.left <= characterPositionX &&
                    characterPositionX <= yesBounds.right &&
                    yesBounds.top <= characterPositionY &&
                    characterPositionY <= yesBounds.bottom
        }
    }

    private fun isNoActionWithinBounds(): Boolean {
        return with(uiState.value) {
            noBounds.left <= characterPositionX &&
                    characterPositionX <= noBounds.right &&
                    noBounds.top <= characterPositionY &&
                    characterPositionY <= noBounds.bottom
        }
    }
}
