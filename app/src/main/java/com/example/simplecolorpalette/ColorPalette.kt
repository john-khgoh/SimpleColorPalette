package com.example.simplecolorpalette

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

/*
@Preview
@Composable
fun ColorPickerDialogSample() {
    ColorPickerDialog(
        uiViewModel = UiViewModel("#000000")
    )
} */

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
                    //uiViewModel.uiState.value.selectedColor.value =
                    val tempColor = "#" + String.format("#%08X", color.toArgb()).takeLast(6)
                    uiViewModel.uiState.value.fontColor.value = tempColor

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
                    //color = Color(android.graphics.Color.parseColor(uiViewModel.uiState.value.selectedColor.value)),
                    color = Color(android.graphics.Color.parseColor(uiViewModel.uiState.value.fontColor.value)),
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
    //var onClick = { uiViewModel.uiState.value.selectedColor.value }
    //var onClick = { uiViewModel.uiState.value.fontColor }
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
                    Circle(
                        color = Color.Unspecified,
                        uiViewModel = uiViewModel,
                        size = circleSize,
                        isDisplayOnly = true
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
                    Image(
                        painter = painterResource(R.drawable.close_24px),
                        contentDescription = null,
                        modifier = Modifier
                            //.padding(0.dp,5.dp,0.dp,0.dp)
                            .size(32.dp)
                            .clickable { uiViewModel.uiState.value.openColorDialog.value = false }
                    )

                }
                Row(
                    modifier = Modifier
                        .padding(20.dp,10.dp)
                    //.padding(20.dp,20.dp,10.dp,10.dp)
                ) {
                    Circle(
                        color = Color(android.graphics.Color.parseColor("#CF0000")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(android.graphics.Color.parseColor("#F34334")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(android.graphics.Color.parseColor("#E71E62")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(android.graphics.Color.parseColor("#9B27AE")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(android.graphics.Color.parseColor("#663AB5")),
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
                        color = Color(android.graphics.Color.parseColor("#3D51B4")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(android.graphics.Color.parseColor("#01A9F2")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(android.graphics.Color.parseColor("#00BCD2")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(android.graphics.Color.parseColor("#009687")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(android.graphics.Color.parseColor("#4BAF4F")),
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
                        color = Color(android.graphics.Color.parseColor("#89C348")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(android.graphics.Color.parseColor("#CCDD39")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(android.graphics.Color.parseColor("#FFEC3A")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(android.graphics.Color.parseColor("#FEC106")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(android.graphics.Color.parseColor("#795547")),
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
                        color = Color(android.graphics.Color.parseColor("#FFFFFF")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(android.graphics.Color.parseColor("#9E9E9E")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(android.graphics.Color.parseColor("#5F7D88")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(android.graphics.Color.parseColor("#415157")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                    Circle(
                        color = Color(android.graphics.Color.parseColor("#000000")),
                        uiViewModel = uiViewModel,
                        size = circleSize,
                    )
                }
                Row() {
                    ColorPicker(onColorChange = {
                        //uiViewModel.uiState.value.selectedColor.value =
                        val tempColor = "#" + String.format("#%08X", it.toArgb()).takeLast(6)
                        uiViewModel.uiState.value.fontColor.value = tempColor
                        uiViewModel.saveFontColor(tempColor)
                    })
                }
            }
        }
    }
}
