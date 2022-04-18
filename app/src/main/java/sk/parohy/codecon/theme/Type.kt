package sk.parohy.codecon.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun typography(isDark: Boolean = isSystemInDarkTheme()) =
    Typography(
        h1 = TextStyle(
            color = if (isDark) text_dark_night else text_dark_light,
            fontSize = 30.sp
        ),
        h2 = TextStyle(
            color = if (isDark) text_dark_night else text_dark_light,
            fontSize = 25.sp
        ),
        subtitle1 = TextStyle(
            color = if (isDark) text_light_night else text_light_light,
            fontSize = 18.sp
        ),
        body1 = TextStyle(
            color = if (isDark) text_dark_night else text_dark_light
        ),
    )