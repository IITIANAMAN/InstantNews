package com.example.newsapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    background = LightBackground,
    primary = PrimaryColor,
    secondary = SecondaryColor,
    onBackground = Color.Black
)

private val DarkColorScheme = darkColorScheme(
    background = DarkBackground,
    primary = PrimaryColor,
    secondary = SecondaryColor,
    onBackground = Color.White
)

@Composable
fun NewsAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
