package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.glassMorphism(
    cornerRadius: Dp = 24.dp,
    blurRadius: Dp = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) 20.dp else 0.dp,
    intensity: Float = 1f
): Modifier {
    return this
        .shadow(elevation = 16.dp, shape = RoundedCornerShape(cornerRadius), spotColor = Color(0x33000000))
        .clip(RoundedCornerShape(cornerRadius))
        .background(
            Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.15f * intensity),
                    Color.White.copy(alpha = 0.05f * intensity)
                )
            )
        )
        .border(
            width = 1.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.4f * intensity),
                    Color.White.copy(alpha = 0.1f * intensity)
                )
            ),
            shape = RoundedCornerShape(cornerRadius)
        )
        .blur(blurRadius)
}
