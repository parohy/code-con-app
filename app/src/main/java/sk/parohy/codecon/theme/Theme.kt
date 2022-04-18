package sk.parohy.codecon.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun CodeConTheme(isDark: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (isDark) codDarkColors else codLightColors,
        typography = typography(isDark)
    ) {
        content()
    }
}