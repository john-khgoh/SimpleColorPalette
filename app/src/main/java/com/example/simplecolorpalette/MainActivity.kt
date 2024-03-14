/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.simplecolorpalette

import android.annotation.SuppressLint
import android.graphics.Color.parseColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.SweepGradientShader
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.view.WindowCompat
import com.example.simplecolorpalette.ui.theme.ColorPickerDemoTheme
import java.util.Locale
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.MutableState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        setContent {
            ColorPickerDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    //ColorPickerDialog(uiViewModel = UiViewModel("#000000"))
                    //ColorPickerDemo()
                    MainMenu(uiViewModel = UiViewModel("#000000"))
                }
            }

        }
    }
}

data class UiViewModel(var selectedColorString: String): ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
}

data class UiState constructor(
    var selectedColor: MutableState<String> = mutableStateOf("#000000"),
    var openColorDialog: MutableState<Boolean> = mutableStateOf(false)
)

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MainMenu(
    uiViewModel: UiViewModel
) {
    //var openColorDialog = remember {mutableStateOf(false)}

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                //.fillMaxHeight(0.2f)
        ) {
            Button(onClick = { uiViewModel.uiState.value.openColorDialog.value = true }) {
                Text("Color Picker")
            }
            when {
                uiViewModel.uiState.value.openColorDialog.value -> {
                    ColorPickerDialog(
                        uiViewModel = uiViewModel
                    )
                }
            }
        }
        Row(
            modifier = Modifier
        ) {
            Button(onClick = {  }) {
                Text("Button 2")
            }
        }
        Row(
            modifier = Modifier
        ) {
            Button(onClick = {  }) {
                Text("Button 3")
            }
        }
    }
}


@Preview
@Composable
fun ColorPickerDialogSample() {
    ColorPickerDialog(
        uiViewModel = UiViewModel("#000000")
    )
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Circle(
    color: Color,
    uiViewModel: UiViewModel,
    size: Int,
    isDisplayOnly: Boolean = false
)
{
    val shape = CircleShape
    //val shape = RoundedCornerShape(20.dp,20.dp,20.dp,20.dp)
    if(!isDisplayOnly) {
        Box(
            modifier = Modifier
                .background(color = color, shape = shape)
                .clip(shape)
                .size(size.dp)
                .clickable {
                    //Formula to reformat the color Argb value to hexadecimal
                    uiViewModel.uiState.value.selectedColor.value =
                        "#" + String
                            .format("#%08X", color.toArgb())
                            .takeLast(6)
                }

        )
        Box(
            modifier = Modifier
                .padding(5.dp)
        )
    }
    else {
        Box(
            modifier = Modifier
                .background(
                    color = Color(parseColor(uiViewModel.uiState.value.selectedColor.value)),
                    shape = shape
                )
                .clip(shape)
                .size(size.dp)
        )
    }
}

@Composable
fun ColorPickerDialog(uiViewModel: UiViewModel) {
    var circleSize = 40
    var onClick = { uiViewModel.uiState.value.selectedColor.value }
    Dialog(onDismissRequest = {uiViewModel.uiState.value.openColorDialog.value = false}) {
        Card(
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier
                .background(color = Color.White)
                .height(600.dp)
                //.width(420.dp)
        ) {
            Column() {
                Row(
                    modifier = Modifier
                        //.padding(20.dp,20.dp)
                        .padding(20.dp,20.dp,10.dp,10.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.close_24px),
                        contentDescription = null,
                        modifier = Modifier
                            //.padding(0.dp,5.dp,0.dp,0.dp)
                            .size(32.dp)
                            .clickable { uiViewModel.uiState.value.openColorDialog.value = false }
                    )
                    Box(
                        modifier = Modifier
                            .padding(20.dp,0.dp,0.dp,0.dp)
                    )
                    Text("Color Picker", fontSize = 22.sp)
                    Box(
                        modifier = Modifier
                            .padding(20.dp,0.dp,0.dp,0.dp)
                    )
                    Circle(
                        color = Color.Unspecified,
                        uiViewModel = uiViewModel,
                        size = circleSize,
                        isDisplayOnly = true
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(20.dp,10.dp)
                        //.padding(20.dp,20.dp,10.dp,10.dp)
                ) {
                    Circle(
                        color = Color(parseColor("#CF0000")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(parseColor("#F34334")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(parseColor("#E71E62")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(parseColor("#9B27AE")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(parseColor("#663AB5")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(20.dp,10.dp)
                        //.padding(20.dp,20.dp,10.dp,10.dp)
                ) {
                    Circle(
                        color = Color(parseColor("#3D51B4")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(parseColor("#01A9F2")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(parseColor("#00BCD2")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(parseColor("#009687")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(parseColor("#4BAF4F")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(20.dp,10.dp)
                        //.padding(20.dp,20.dp,10.dp,10.dp)
                ) {
                    Circle(
                        color = Color(parseColor("#89C348")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(parseColor("#CCDD39")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(parseColor("#FFEC3A")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(parseColor("#FEC106")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(parseColor("#795547")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(20.dp,10.dp)
                        //.padding(20.dp,20.dp,10.dp,10.dp)
                ) {
                    Circle(
                        color = Color(parseColor("#FFFFFF")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(parseColor("#9E9E9E")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(parseColor("#5F7D88")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(parseColor("#415157")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(parseColor("#000000")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                }
                Row() {
                    ColorPicker(onColorChange = {
                        uiViewModel.uiState.value.selectedColor.value =
                        "#" + String
                            .format("#%08X", it.toArgb())
                            .takeLast(6)
                    })
                }
            }
        }
    }
}

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPickerDemo() {
    var primary by remember { mutableStateOf(Color(0xFF6200EE)) }
    Surface(color = Color(0xFF121212)) {
        Column {
            TopAppBar(title = { Text("Color Picker") }, colors = TopAppBarDefaults.smallTopAppBarColors(primary))
            ColorPicker(onColorChange = { primary = it })
        }
    }
} */

@Composable
private fun ColorPicker(onColorChange: (Color) -> Unit) {
    BoxWithConstraints(
        Modifier
            .padding(50.dp)
            //.fillMaxSize()
            .aspectRatio(1f)
    ) {
        //val diameter = constraints.maxWidth
        val diameter = 600
        var position by remember { mutableStateOf(Offset.Zero) }
        val colorWheel = remember(diameter) { ColorWheel(diameter) }

        var hasInput by remember { mutableStateOf(false) }
        val inputModifier = Modifier.pointerInput(colorWheel) {
            fun updateColorWheel(newPosition: Offset) {
                // Work out if the new position is inside the circle we are drawing, and has a
                // valid color associated to it. If not, keep the current position
                val newColor = colorWheel.colorForPosition(newPosition)
                if (newColor.isSpecified) {
                    position = newPosition
                    onColorChange(newColor)
                }
            }

            awaitEachGesture {
                val down = awaitFirstDown()
                hasInput = true
                updateColorWheel(down.position)
                drag(down.id) { change ->
                    change.consume()
                    updateColorWheel(change.position)
                }
                hasInput = false
            }
        }

        Box(Modifier.fillMaxSize()) {
            Image(modifier = inputModifier, contentDescription = null, bitmap = colorWheel.image)
            val color = colorWheel.colorForPosition(position)
            if (color.isSpecified) {
                Magnifier(visible = hasInput, position = position, color = color)
            }
        }
    }
}

/**
 * Magnifier displayed on top of [position] with the currently selected [color].
 */
@Composable
private fun Magnifier(visible: Boolean, position: Offset, color: Color) {
    val offset = with(LocalDensity.current) {
        Modifier.offset(
            position.x.toDp() - magnifierWidth / 2,
            // Align with the center of the selection circle
            position.y.toDp() - (magnifierHeight - (selectionCircleDiameter / 2))
        )
    }
    MagnifierTransition(
        visible,
        magnifierWidth,
        selectionCircleDiameter
    ) { labelWidth: Dp, selectionDiameter: Dp,
        alpha: Float ->
        Column(
            offset
                .size(width = magnifierWidth, height = magnifierHeight)
                .alpha(alpha)
        ) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                MagnifierLabel(Modifier.size(labelWidth, magnifierLabelHeight), color)
            }
            Spacer(Modifier.weight(1f))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(selectionCircleDiameter),
                contentAlignment = Alignment.Center
            ) {
                MagnifierSelectionCircle(Modifier.size(selectionDiameter), color)
            }
        }
    }
}

private val magnifierWidth = 110.dp
private val magnifierHeight = 100.dp
private val magnifierLabelHeight = 50.dp
private val selectionCircleDiameter = 30.dp

/**
 * [Transition] that animates between [visible] states of the magnifier by animating the width of
 * the label, diameter of the selection circle, and alpha of the overall magnifier
 */
@Composable
private fun MagnifierTransition(
    visible: Boolean,
    maxWidth: Dp,
    maxDiameter: Dp,
    content: @Composable (labelWidth: Dp, selectionDiameter: Dp, alpha: Float) -> Unit
) {
    val transition = updateTransition(visible, label = "")
    val labelWidth by transition.animateDp(transitionSpec = { tween() }, label = "") {
        if (it) maxWidth else 0.dp
    }
    val magnifierDiameter by transition.animateDp(transitionSpec = { tween() }, label = "") {
        if (it) maxDiameter else 0.dp
    }
    val alpha by transition.animateFloat(
        transitionSpec = {
            if (true isTransitioningTo false) {
                tween(delayMillis = 100, durationMillis = 200)
            } else {
                tween()
            }
        }, label = ""
    ) {
        if (it) 1f else 0f
    }
    content(labelWidth, magnifierDiameter, alpha)
}

/**
 * Label representing the currently selected [color], with [Text] representing the hex code and a
 * square at the start showing the [color].
 */
@Composable
private fun MagnifierLabel(modifier: Modifier, color: Color) {
    Surface(shape = magnifierPopupShape) {
        Row(modifier) {
            Box(
                Modifier
                    .weight(0.25f)
                    .fillMaxHeight()
                    .background(color))
            // Add `#` and drop alpha characters
            val text = "#" + Integer.toHexString(color.toArgb()).uppercase(Locale.ROOT).drop(2)
            val textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
            Text(
                text = text,
                modifier = Modifier
                    .weight(0.75f)
                    .padding(top = 10.dp, bottom = 20.dp),
                style = textStyle,
                maxLines = 1
            )
        }
    }
}

/**
 * Selection circle drawn over the currently selected pixel of the color wheel.
 */
@Composable
private fun MagnifierSelectionCircle(modifier: Modifier, color: Color) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = color,
        border = BorderStroke(2.dp, SolidColor(Color.Black.copy(alpha = 0.75f))),
        content = {}
    )

}

/**
 * A [GenericShape] that draws a box with a triangle at the bottom center to indicate a popup.
 */
private val magnifierPopupShape = GenericShape { size, _ ->
    val width = size.width
    val height = size.height

    val arrowY = height * 0.8f
    val arrowXOffset = width * 0.4f

    addRoundRect(RoundRect(0f, 0f, width, arrowY, cornerRadius = CornerRadius(20f, 20f)))

    moveTo(arrowXOffset, arrowY)
    lineTo(width / 2f, height)
    lineTo(width - arrowXOffset, arrowY)
    close()
}

/**
 * A color wheel with an [ImageBitmap] that draws a circular color wheel of the specified diameter.
 */
private class ColorWheel(diameter: Int) {

    private val radius = diameter / 2f

    private val sweepGradient = SweepGradientShader(
        colors = listOf(
            Color.Red,
            Color.Magenta,
            Color.Blue,
            Color.Cyan,
            Color.Green,
            Color.Yellow,
            Color.Red
        ),
        colorStops = null,
        center = Offset(radius, radius)
    )

    val image = ImageBitmap(diameter, diameter).also { imageBitmap ->
        val canvas = Canvas(imageBitmap)
        val center = Offset(radius, radius)
        val paint = Paint().apply { shader = sweepGradient }
        canvas.drawCircle(center, radius, paint)
    }
}

/**
 * @return the matching color for [position] inside [ColorWheel], or `null` if there is no color
 * or the color is partially transparent.
 */
private fun ColorWheel.colorForPosition(position: Offset): Color {
    val x = position.x.toInt().coerceAtLeast(0)
    val y = position.y.toInt().coerceAtLeast(0)
    with(image.toPixelMap()) {
        if (x >= width || y >= height) return Color.Unspecified
        return this[x, y].takeIf { it.alpha == 1f } ?: Color.Unspecified
    }
}