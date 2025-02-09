package com.phoenix.valentine.screen.gameboy

sealed interface GameBoyUiEvent {
    data class UpdateCharacterPosition(val positionChange: PositionChange): GameBoyUiEvent
    data class OnClickActionButton(val actionButton: ActionButton): GameBoyUiEvent
    data object OnClickStartSelect: GameBoyUiEvent
}

enum class PositionChange { LEFT, RIGHT, UP, DOWN }
enum class ActionButton { A, B }