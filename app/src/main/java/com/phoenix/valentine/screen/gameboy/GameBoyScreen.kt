package com.phoenix.valentine.screen.gameboy

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phoenix.valentine.ui.theme.ClippedCircleShape

@Composable
fun GameBoyScreen() {
    Scaffold { innerPadding ->
        // GameBoyUi
        Column(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(9 / 16f)
                .padding(innerPadding)
                .padding(vertical = 24.dp, horizontal = 16.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.onSurface)
        ) {
            // Screen
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(5f)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray)
            )
            // Controls
            GameBoyControls(
                Modifier
                    .fillMaxSize()
                    .weight(4f)
            )
        }
    }
}

@Composable
private fun GameBoyControls(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // D-Pad
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row {
                    Box(
                        modifier = Modifier
                            .height(36.dp)
                            .width(36.dp)
                            .background(Color.Gray)
                            .clickable {

                            }
                    )
                }
                Row {
                    Box(
                        modifier = Modifier
                            .height(36.dp)
                            .width(36.dp)
                            .background(Color.Gray)
                            .clickable {

                            }
                    )
                    Box(
                        modifier = Modifier
                            .height(36.dp)
                            .width(36.dp)
                            .background(Color.Gray)
                    )
                    Box(
                        modifier = Modifier
                            .height(36.dp)
                            .width(36.dp)
                            .background(Color.Gray)
                            .clickable {

                            }
                    )
                }
                Row {
                    Box(
                        modifier = Modifier
                            .height(36.dp)
                            .width(36.dp)
                            .background(Color.Gray)
                            .clickable {

                            }
                    )
                }
            }
            // A, B
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .padding(start = 36.dp)
                        .size(48.dp)
                        .clip(ClippedCircleShape())
                        .background(Color.Red)
                        .clickable {

                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "A",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.surface,
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(end = 36.dp)
                        .size(48.dp)
                        .clip(ClippedCircleShape())
                        .background(Color.Red)
                        .clickable {

                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "B",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.surface,
                    )
                }
            }
        }
        Spacer(Modifier.height(24.dp))
        // Select, Start
        Row {
            Column(
                modifier = Modifier.rotate(-25f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .height(12.dp)
                        .width(36.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Gray)
                        .clickable {

                        }
                )
                Text(
                    text = "SELECT",
                    color = MaterialTheme.colorScheme.surface,
                    fontSize = 8.sp
                )
            }
            Spacer(Modifier.width(16.dp))
            Column(
                modifier = Modifier.rotate(-25f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .height(12.dp)
                        .width(36.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Gray)
                        .clickable {

                        }
                )
                Text(
                    text = "START",
                    color = MaterialTheme.colorScheme.surface,
                    fontSize = 8.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun GameBoyScreenPreview() {
    GameBoyScreen()
}