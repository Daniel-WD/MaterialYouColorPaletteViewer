package com.titaniel.materialyoucolorpaletteviewer.ui.screens

import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.titaniel.materialyoucolorpaletteviewer.ui.theme.MaterialYouColorPaletteViewerTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaletteViewModel @Inject constructor(context: Application) : AndroidViewModel(context) {

    companion object {

        /**
         * List of lists for the sub lists being rows with neutral1, neutral2, accent1, accent2 and
         * accent3 Material You colors.
         */
        val COLOR_MATRIX = listOf(
            listOf(
                android.R.color.system_neutral1_0,
                android.R.color.system_neutral2_0,
                android.R.color.system_accent1_0,
                android.R.color.system_accent2_0,
                android.R.color.system_accent3_0
            ),
            listOf(
                android.R.color.system_neutral1_10,
                android.R.color.system_neutral2_10,
                android.R.color.system_accent1_10,
                android.R.color.system_accent2_10,
                android.R.color.system_accent3_10
            ),
            listOf(
                android.R.color.system_neutral1_50,
                android.R.color.system_neutral2_50,
                android.R.color.system_accent1_50,
                android.R.color.system_accent2_50,
                android.R.color.system_accent3_50
            ),
            listOf(
                android.R.color.system_neutral1_100,
                android.R.color.system_neutral2_100,
                android.R.color.system_accent1_100,
                android.R.color.system_accent2_100,
                android.R.color.system_accent3_100
            ),
            listOf(
                android.R.color.system_neutral1_200,
                android.R.color.system_neutral2_200,
                android.R.color.system_accent1_200,
                android.R.color.system_accent2_200,
                android.R.color.system_accent3_200
            ),
            listOf(
                android.R.color.system_neutral1_300,
                android.R.color.system_neutral2_300,
                android.R.color.system_accent1_300,
                android.R.color.system_accent2_300,
                android.R.color.system_accent3_300
            ),
            listOf(
                android.R.color.system_neutral1_400,
                android.R.color.system_neutral2_400,
                android.R.color.system_accent1_400,
                android.R.color.system_accent2_400,
                android.R.color.system_accent3_400
            ),
            listOf(
                android.R.color.system_neutral1_500,
                android.R.color.system_neutral2_500,
                android.R.color.system_accent1_500,
                android.R.color.system_accent2_500,
                android.R.color.system_accent3_500
            ),
            listOf(
                android.R.color.system_neutral1_600,
                android.R.color.system_neutral2_600,
                android.R.color.system_accent1_600,
                android.R.color.system_accent2_600,
                android.R.color.system_accent3_600
            ),
            listOf(
                android.R.color.system_neutral1_700,
                android.R.color.system_neutral2_700,
                android.R.color.system_accent1_700,
                android.R.color.system_accent2_700,
                android.R.color.system_accent3_700
            ),
            listOf(
                android.R.color.system_neutral1_800,
                android.R.color.system_neutral2_800,
                android.R.color.system_accent1_800,
                android.R.color.system_accent2_800,
                android.R.color.system_accent3_800
            ),
            listOf(
                android.R.color.system_neutral1_900,
                android.R.color.system_neutral2_900,
                android.R.color.system_accent1_900,
                android.R.color.system_accent2_900,
                android.R.color.system_accent3_900
            ),
            listOf(
                android.R.color.system_neutral1_1000,
                android.R.color.system_neutral2_1000,
                android.R.color.system_accent1_1000,
                android.R.color.system_accent2_1000,
                android.R.color.system_accent3_1000
            )
        )

        /**
         * Labels for color shades.
         */
        val SHADE_LABELS = listOf(
            "0",
            "10",
            "50",
            "100",
            "200",
            "300",
            "400",
            "500",
            "600",
            "700",
            "800",
            "900",
            "1000"
        )

        /**
         * Labels for different colors.
         */
        val COLOR_NAMES = listOf("N1", "N2", "A1", "A2", "A3")
    }

    /**
     * Contains values of [COLOR_MATRIX] that exist on this device.
     */
    private val _colorMatrix: MutableLiveData<List<List<Int>>> = MutableLiveData()
    val colorMatrix: LiveData<List<List<Int>>> = _colorMatrix

    init {

        // Like COLOR_MATRIX excluding non existing colors. Those will be black.
        _colorMatrix.value = COLOR_MATRIX.map {

            // Map each row to its row with working colors
            it.map { colorId ->

                try {

                    // If this works
                    context.getColor(colorId)

                    // Return color
                    colorId
                } catch (e: Exception) {

                    // Return black otherwise
                    android.R.color.black
                }

            }

        }

    }

}

@Composable
fun PaletteScreenWrapper(viewModel: PaletteViewModel = viewModel()) {

    val colorIdMatrix by viewModel.colorMatrix.observeAsState(emptyList())

    // Create color matrix out of color id matrix
    val colorMatrix = colorIdMatrix.map { it.map { colorId -> colorResource(id = colorId) } }

    PaletteScreen(colorMatrix = colorMatrix)
}

@Composable
fun PaletteScreen(colorMatrix: List<List<Color>>) {

    SetSystemUiColors()

    Column(modifier = Modifier.fillMaxSize()) {

        TopAppBar(title = {
            Text(text = "Material You Color Palette")
        }, backgroundColor = MaterialTheme.colors.surface)

        Column(modifier = Modifier.padding(16.dp)) {

            val yAxisWidth = 41.dp

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = yAxisWidth),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                PaletteViewModel.COLOR_NAMES.forEach {

                    Text(text = it, textAlign = TextAlign.Center)
                }
            }

            BoxWithConstraints {

                // Calculate width and height of tiles
                val tileHeight = maxHeight / 13

                Row {

                    Column(
                        modifier = Modifier
                            .width(yAxisWidth)
                            .padding(end = 4.dp),
                        horizontalAlignment = Alignment.End
                    ) {

                        PaletteViewModel.SHADE_LABELS.forEach {

                            Box(
                                modifier = Modifier.height(tileHeight),
                                contentAlignment = Alignment.CenterEnd
                            ) {

                                Text(text = it)
                            }
                        }
                    }

                    BoxWithConstraints {

                        val tileWidth = maxWidth / 5

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            colorMatrix.forEach { colorRow ->

                                Row {

                                    colorRow.forEach { color ->

                                        Tile(
                                            color = color,
                                            width = tileWidth,
                                            height = tileHeight
                                        )
                                    }
                                }
                            }
                        }
                    }

                }

            }
        }


    }


}

@Composable
fun Tile(color: Color, width: Dp, height: Dp) {

    Surface(
        modifier = Modifier
            .size(width = width, height = height)
            .padding(4.dp),
        shape = MaterialTheme.shapes.small,
        color = color,
        border = BorderStroke(
            1.dp,
            // Use black border when light theme and white border on dark theme for better background contrast
            (if (MaterialTheme.colors.isLight) Color.Black else Color.White).copy(alpha = 0.1f)
        )
    ) {}
}

@Composable
fun SetSystemUiColors() {

    // Get system ui controller
    val systemUiController = rememberSystemUiController()

    // Get color for status bar
    val statusBarColor = MaterialTheme.colors.surface

    // Get color for navigation bar
    val navigationBarColor = MaterialTheme.colors.background

    SideEffect {

        // Set system bar color
        systemUiController.setSystemBarsColor(color = statusBarColor)

        // Set navigation bar color
        systemUiController.setNavigationBarColor(color = navigationBarColor)
    }

}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun PalettePreview() {

    val tileColor = Color(0xFFFFE0E0)

    MaterialYouColorPaletteViewerTheme {
        PaletteScreen(
            listOf(
                listOf(
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor
                ),
                listOf(
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor
                ),
                listOf(
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor
                ),
                listOf(
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor
                ),
                listOf(
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor
                ),
                listOf(
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor
                ),
                listOf(
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor
                ),
                listOf(
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor
                ),
                listOf(
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor
                ),
                listOf(
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor
                ),
                listOf(
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor
                ),
                listOf(
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor
                ),
                listOf(
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor,
                    tileColor
                )
            )
        )
    }
}