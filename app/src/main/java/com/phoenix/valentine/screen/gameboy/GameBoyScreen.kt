package com.phoenix.valentine.screen.gameboy

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toIntSize
import com.phoenix.valentine.R
import com.phoenix.valentine.model.CharacterAnimationState
import com.phoenix.valentine.model.CharacterDirection
import com.phoenix.valentine.model.CharacterSprite
import com.phoenix.valentine.ui.theme.ClippedCircleShape
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GameBoyScreen(
    characterPosition: Offset,
    characterDirection: CharacterDirection,
    onPositionChange: (PositionChange) -> Unit,
    actionState: ActionState,
    onAction: () -> Unit,
    isCreditDisplayed: Boolean,
    requestDisplayCredit: () -> Unit,
    requestRemoveCredit: () -> Unit,
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
            GameBoyCanvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                displayCredit = isCreditDisplayed,
                characterOffset = characterPosition,
                characterDirection = characterDirection,
                isMoving = isDPadPressing,
                actionState = actionState
            )
            GameBoyControls(
                Modifier
                    .fillMaxSize()
                    .weight(1f),
                onClickDPad = {
                    isDPadPressing = true
                    coroutineScope.launch {
                        while (isDPadPressing) {
                            onPositionChange(it)
                            delay(50)
                        }
                    }
                },
                releaseDPad = { isDPadPressing = false },
                onClickA = { onAction() },
                onClickB = { requestRemoveCredit() },
                onClickStartSelect = { requestDisplayCredit() }
            )
        }
    }
}

@Composable
private fun GameBoyCanvas(
    modifier: Modifier = Modifier,
    displayCredit: Boolean,
    characterOffset: Offset,
    characterDirection: CharacterDirection,
    isMoving: Boolean,
    actionState: ActionState
) {
    // Init screens
    val normalImage = painterResource(id = R.drawable.canvas_normal)
    val yesImage = painterResource(id = R.drawable.canvas_yes)
    val creditImage = painterResource(id = R.drawable.canvas_credit)

    val achivement = ImageBitmap.imageResource(id = R.drawable.achivement)

    // Init cat animation variants
    val catLeft1 = ImageBitmap.imageResource(id = R.drawable.cat_left_1)
    val catLeft2 = ImageBitmap.imageResource(id = R.drawable.cat_left_2)
    val catRight1 = ImageBitmap.imageResource(id = R.drawable.cat_right_1)
    val catRight2 = ImageBitmap.imageResource(id = R.drawable.cat_right_2)
    val catUp1 = ImageBitmap.imageResource(id = R.drawable.cat_up_1)
    val catUp2 = ImageBitmap.imageResource(id = R.drawable.cat_up_2)
    val catDown1 = ImageBitmap.imageResource(id = R.drawable.cat_down_1)
    val catDown2 = ImageBitmap.imageResource(id = R.drawable.cat_down_2)

    // Load all character sprites
    val characterSprites = remember {
        listOf(
            CharacterSprite(
                catLeft1,
                CharacterDirection.LEFT,
                CharacterAnimationState.WALK_1
            ),
            CharacterSprite(
                catLeft2,
                CharacterDirection.LEFT,
                CharacterAnimationState.WALK_2
            ),
            CharacterSprite(
                catRight1,
                CharacterDirection.RIGHT,
                CharacterAnimationState.WALK_1
            ),
            CharacterSprite(
                catRight2,
                CharacterDirection.RIGHT,
                CharacterAnimationState.WALK_2
            ),
            CharacterSprite(
                catUp1,
                CharacterDirection.UP,
                CharacterAnimationState.WALK_1
            ),
            CharacterSprite(
                catUp2,
                CharacterDirection.UP,
                CharacterAnimationState.WALK_2
            ),
            CharacterSprite(
                catDown1,
                CharacterDirection.DOWN,
                CharacterAnimationState.WALK_1
            ),
            CharacterSprite(
                catDown2,
                CharacterDirection.DOWN,
                CharacterAnimationState.WALK_2
            ),
        )
    }

    // Animation state
    var currentAnimationState by remember { mutableIntStateOf(0) }
    LaunchedEffect(isMoving) {
        if (isMoving) {
            while (true) {
                delay(250L)
                currentAnimationState = (currentAnimationState + 1) % 2
            }
        }
    }

    val fadeInAnimation = remember { Animatable(0f) }
    val slideUpAnimation = remember { Animatable(0f) }

    LaunchedEffect(actionState == ActionState.YES) {
        if (actionState == ActionState.YES) {
            fadeInAnimation.animateTo(1f, animationSpec = tween(durationMillis = 500))
            slideUpAnimation.animateTo(
                1f,
                animationSpec = tween(durationMillis = 500, delayMillis = 1000)
            )
        }
    }

    Canvas(
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        if (displayCredit) {
            with(creditImage) {
                draw(size)
            }
            return@Canvas
        }

        with(normalImage) {
            draw(size)
        }

        // Determine the correct sprite based on direction and animation state
        val currentSprite = characterSprites.firstOrNull {
            it.direction == characterDirection &&
                    (it.animationState == CharacterAnimationState.entries.toTypedArray()[currentAnimationState])
        }

        currentSprite?.let {
            drawImage(
                image = it.image,
                dstSize = (size * 0.075f).toIntSize(),
                dstOffset = IntOffset(
                    x = (size.width * characterOffset.x).toInt(),
                    y = (size.height * characterOffset.y).toInt()
                )
            )
        }

        if (actionState == ActionState.NO) {

        }

        if (actionState == ActionState.YES) {
            with(yesImage) {
                draw(size, alpha = fadeInAnimation.value)
            }
            drawImage(
                image = achivement,
                topLeft = Offset(
                    x = (size.width / 6),
                    y = (size.height) - (size.height * 0.18f * slideUpAnimation.value)
                )
            )
            return@Canvas
        }
    }
}

@Composable
private fun GameBoyControls(
    modifier: Modifier = Modifier,
    onClickDPad: (PositionChange) -> Unit,
    releaseDPad: () -> Unit,
    onClickA: () -> Unit,
    onClickB: () -> Unit,
    onClickStartSelect: () -> Unit
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
            GameBoyDPad(
                onClickDPad = onClickDPad,
                releaseDPad = releaseDPad
            )
            GameBoyActionButtons(
                onClickA = onClickA,
                onClickB = onClickB
            )

        }
        Spacer(Modifier.height(24.dp))
        GameBoyStartSelect(
            onClickStartSelect = onClickStartSelect
        )
    }
}

@Composable
fun GameBoyDPad(
    onClickDPad: (PositionChange) -> Unit,
    releaseDPad: () -> Unit
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
}

@Composable
fun GameBoyActionButtons(
    onClickA: () -> Unit,
    onClickB: () -> Unit
) {
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
                    onClickA()
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
                    onClickB()
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

@Composable
fun GameBoyStartSelect(
    onClickStartSelect: () -> Unit
) {
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
                        onClickStartSelect()
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
                        onClickStartSelect()
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

@Preview
@Composable
fun GameBoyScreenPreview() {
    val uiState by remember { mutableStateOf(GameBoyUiState()) }
    GameBoyScreen(
        characterPosition = Offset(
            x = uiState.characterPositionX,
            y = uiState.characterPositionY
        ),
        characterDirection = uiState.characterDirection,
        onPositionChange = {},
        isCreditDisplayed = false,
        requestDisplayCredit = {},
        requestRemoveCredit = {},
        actionState = ActionState.NONE,
        onAction = {}
    )
}