package com.phoenix.valentine.screen.gameboy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun GameBoyRoute(
    gameBoyViewModel: GameBoyViewModel = hiltViewModel<GameBoyViewModel>()
) {
    val uiState by gameBoyViewModel.uiState.collectAsStateWithLifecycle()

    GameBoyScreen(
        characterPosition = Offset(uiState.characterPositionX, uiState.characterPositionY),
        onPositionChange = { gameBoyViewModel.onEvent(GameBoyUiEvent.UpdateCharacterPosition(it)) }
    )
}