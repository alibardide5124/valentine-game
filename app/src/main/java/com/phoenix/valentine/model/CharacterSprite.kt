package com.phoenix.valentine.model

import androidx.compose.ui.graphics.ImageBitmap

data class CharacterSprite(
    val image: ImageBitmap,
    val direction: CharacterDirection,
    val animationState: CharacterAnimationState
)

enum class CharacterDirection {
    LEFT, RIGHT, UP, DOWN
}

enum class CharacterAnimationState {
    WALK_1, WALK_2
}