package com.phoenix.valentine.screen.gameboy

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toIntSize
import com.phoenix.valentine.R
import com.phoenix.valentine.ui.theme.ClippedCircleShape
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GameBoyScreen(
    characterPosition: Offset,
    onPositionChange: (PositionChange) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var isDPadPressing by remember { mutableStateOf(false) }

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
            GameBoyCanvas(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                characterOffset = characterPosition
            )
            // Controls
            GameBoyControls(
                Modifier
                    .fillMaxSize()
                    .weight(1f),
                onClickDPad = {
                    isDPadPressing = true
                    coroutineScope.launch {
                        do {
                            onPositionChange(it)
                            delay(50)
                        } while(isDPadPressing)
                    }
                },
                releaseDPad = { isDPadPressing = false }
            )
        }
    }
}

@Composable
private fun GameBoyCanvas(
    modifier: Modifier = Modifier,
    characterOffset: Offset
) {
    val normalImage = painterResource(id = R.drawable.canvas_normal)
    val catFrontLeft = ImageBitmap.imageResource(id = R.drawable.cat_front_left)

    Canvas(
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        with(normalImage) {
            draw(size)
        }
        drawImage(
            image = catFrontLeft.apply { },
            dstSize = (size * 0.075f).toIntSize(),
            dstOffset = IntOffset(
                x = (size.width * characterOffset.x).toInt(),
                y = (size.height * characterOffset.y).toInt()
            )
        )
    }
}

@Composable
private fun GameBoyControls(
    modifier: Modifier = Modifier,
    onClickDPad: (PositionChange) -> Unit,
    releaseDPad: () -> Unit
) {
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
                // TOP D-Pad
                Box(
                    modifier = Modifier
                        .height(36.dp)
                        .width(36.dp)
                        .background(Color.Gray)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    try {
                                        onClickDPad(PositionChange.UP)
                                        awaitRelease()
                                    } finally {
                                        releaseDPad()
                                    }
                                },
                            )
                        }
                )
                Row {
                    // LEFT D-Pad
                    Box(
                        modifier = Modifier
                            .height(36.dp)
                            .width(36.dp)
                            .background(Color.Gray)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = {
                                        try {
                                            onClickDPad(PositionChange.LEFT)
                                            awaitRelease()
                                        } finally {
                                            releaseDPad()
                                        }
                                    },
                                )
                            }
                    )
                    Box(
                        modifier = Modifier
                            .height(36.dp)
                            .width(36.dp)
                            .background(Color.Gray)
                    )
                    // RIGHT D-Pad
                    Box(
                        modifier = Modifier
                            .height(36.dp)
                            .width(36.dp)
                            .background(Color.Gray)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = {
                                        try {
                                            onClickDPad(PositionChange.RIGHT)
                                            awaitRelease()
                                        } finally {
                                            releaseDPad()
                                        }
                                    },
                                )
                            }
                    )
                }
                // BOTTOM D-Pad
                Box(
                    modifier = Modifier
                        .height(36.dp)
                        .width(36.dp)
                        .background(Color.Gray)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    try {
                                        onClickDPad(PositionChange.DOWN)
                                        awaitRelease()
                                    } finally {
                                        releaseDPad()
                                    }
                                },
                            )
                        }
                )
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
    GameBoyScreen(
        characterPosition = Offset(
            x = GameBoyUiState().characterPositionX,
            y = GameBoyUiState().characterPositionY
        ),
        onPositionChange = {}
    )
}