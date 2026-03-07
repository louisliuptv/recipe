package com.example.colesexercise.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColesRed = Color(0xFFE01A22)

private val ColorScheme = lightColorScheme(
    primary = ColesRed,
    onPrimary = Color.White,
    primaryContainer = ColesRed,
    onPrimaryContainer = Color.White,
    secondary = Color(0xFF4A4A4A),
    onSecondary = Color.White,
    background = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),
    surface = Color.White,
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFF5F5F5),
    onSurfaceVariant = Color(0xFF6B6B6B),
    error = Color(0xFFB3261E)
)

@Composable
fun ColesExerciseTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ColorScheme,
        content = content
    )
}

