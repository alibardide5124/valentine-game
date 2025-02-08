package com.phoenix.valentine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.phoenix.valentine.screen.gameboy.GameBoyRoute
import com.phoenix.valentine.ui.theme.ValentineGameTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ValentineGameTheme {
                Surface {
                    GameBoyRoute()
                }
            }
        }
    }
}