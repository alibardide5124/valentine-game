package com.phoenix.valentine.screen.gameboy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun GameBoyRoute(
    gameBoyViewModel: GameBoyViewModel = hiltViewModel<GameBoyViewModel>()
) {
    val uiState by gameBoyViewModel.uiState.collectAsStateWithLifecycle()

    GameBoyScreen()
}