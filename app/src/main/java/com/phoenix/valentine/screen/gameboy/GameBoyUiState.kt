package com.phoenix.valentine.screen.gameboy

data class GameBoyUiState(
    val characterPositionX: Float = 0.5f,
    val characterPositionY: Float = 0.8f,
    val characterBoundX: ClosedFloatingPointRange<Float> = 0.05f..0.95f,
    val characterBoundY: ClosedFloatingPointRange<Float> = 0.35f..0.95f
)