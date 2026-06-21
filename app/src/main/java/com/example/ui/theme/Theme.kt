package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val YarmoukColorScheme = darkColorScheme(
    primary = YarmoukAccentNeo,
    secondary = YarmoukBlue,
    tertiary = YarmoukSurfaceVariant,
    background = YarmoukBg,
    surface = YarmoukSurface,
    surfaceVariant = YarmoukSurfaceVariant,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = TextPrimaryLight,
    onSurface = TextPrimaryLight,
    onSurfaceVariant = TextSecondaryLight,
    outlineVariant = Color(0xFF444444)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Force dark theme for institutional layout
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = YarmoukColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
