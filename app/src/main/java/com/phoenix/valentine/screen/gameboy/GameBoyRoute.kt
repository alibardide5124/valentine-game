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
        characterDirection = uiState.characterDirection,
        onPositionChange = { gameBoyViewModel.onEvent(GameBoyUiEvent.UpdateCharacterPosition(it)) },
        displayCredit = uiState.displayCredit,
        goToCredit = { gameBoyViewModel.onEvent(GameBoyUiEvent.OnClickStartSelect) },
        removeCredit = { gameBoyViewModel.onEvent(GameBoyUiEvent.OnClickActionButton(ActionButton.B)) }
    )
}