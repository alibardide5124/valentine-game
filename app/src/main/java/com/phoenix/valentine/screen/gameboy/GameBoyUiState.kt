package com.phoenix.valentine.screen.gameboy

import com.phoenix.valentine.model.CharacterDirection

data class GameBoyUiState(
    val displayCredit: Boolean = false,
    val characterPositionX: Float = 0.5f,
    val characterPositionY: Float = 0.8f,
    val characterDirection: CharacterDirection = CharacterDirection.DOWN,
    val characterBounds: CharacterBounds = CharacterBounds(
        top = 0.35f,
        left = 0.05f,
        bottom = 0.95f,
        right = 0.95f
    )
)

data class CharacterBounds(
    val top: Float,
    val left: Float,
    val bottom: Float,
    val right: Float
)