package sk.parohy.codecon.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val white = Color(0xFFFFFFFF)

val codecon_light = Color(0xFF7351c8)
val codecon_dark_light = Color(0xFF3D2677)

val text_dark_light = Color(0xFF160D2E)
val text_light_light = Color(0xFF35323C)

val codecon_secondary_light = Color(0xFFA23F2D)
val codecon_secondary_dark_light = Color(0xFFec755a)

val codecon_night = Color(0xFF3F2C70)
val codecon_dark_night = Color(0xFF381B81)

// dark

val text_dark_night = Color(0xFFA29DB1)
val text_light_night = Color(0xFF706E77)

val codecon_secondary_night = Color(0xFF7C2E1F)
val codecon_secondary_dark_night = Color(0xFF9F4E3C)

val codLightColors = lightColors(
    primary = codecon_light,
    primaryVariant = codecon_dark_light,
    onPrimary = white,
    secondary = codecon_secondary_light,
    secondaryVariant = codecon_secondary_dark_light,
    onSecondary = white
)

val codDarkColors = darkColors(
    primary = codecon_night,
    primaryVariant = codecon_dark_night,
    onPrimary = white,
    secondary = codecon_secondary_night,
    secondaryVariant = codecon_secondary_dark_night,
    onSecondary = white
)