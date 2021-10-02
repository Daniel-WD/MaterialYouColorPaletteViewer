package com.titaniel.materialyoucolorpaletteviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.titaniel.materialyoucolorpaletteviewer.ui.screens.PaletteScreenWrapper
import com.titaniel.materialyoucolorpaletteviewer.ui.theme.MaterialYouColorPaletteViewerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialYouColorPaletteViewerTheme {
                Surface(color = MaterialTheme.colors.background) {
                    PaletteScreenWrapper()
                }
            }
        }
    }
}