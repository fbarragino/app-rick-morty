package com.example.rickmorty.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val RickColorScheme = darkColorScheme(
    primary = RickPrimary,
    onPrimary = Color.Black,
    secondary = RickAccent,
    background = RickDarkBlue,
    surface = RickSurface,
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = RickTextSecondary
)

@Composable
fun RickMortyTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = RickColorScheme,
        typography = Typography,
        content = content
    )
}
