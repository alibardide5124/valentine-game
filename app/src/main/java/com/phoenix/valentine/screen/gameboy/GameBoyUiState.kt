package com.phoenix.valentine.screen.gameboy

import com.phoenix.valentine.model.CharacterDirection

data class GameBoyUiState(
    val displayCharacter: Boolean = true,
    val displayCredit: Boolean = false,
    val characterPositionX: Float = 0.5f,
    val characterPositionY: Float = 0.8f,
    val characterDirection: CharacterDirection = CharacterDirection.DOWN,
    val characterBounds: Bounds = Bounds(
        top = 0.35f,
        left = 0.05f,
        bottom = 0.95f,
        right = 0.95f
    ),
    val actionState: ActionState = ActionState.NONE,
    val yesBounds: Bounds = Bounds(
        top = 0.52f,
        left = 0.0f,
        bottom = 0.62f,
        right = 0.22f
    ),
    val noBounds: Bounds = Bounds(
        top = 0.52f,
        left = 0.76f,
        bottom = 0.62f,
        right = 0.94f
    ),
    val displayHole: Boolean = false,
    val noAttempts: Int = 0,
)

data class Bounds(
    val top: Float,
    val left: Float,
    val bottom: Float,
    val right: Float
)

enum class ActionState { NONE, YES, NO }