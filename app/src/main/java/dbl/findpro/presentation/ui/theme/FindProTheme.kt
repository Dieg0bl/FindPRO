package dbl.findpro.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 🎨 Paleta de colores
val PrimaryColor = Color(0xFF6750A4)
val PrimaryLightColor = Color(0xFFD0BCFF)
val PrimaryDarkColor = Color(0xFF4F378B)
val SecondaryColor = Color(0xFF625B71)
val SecondaryDarkColor = Color(0xFF4A4458)
val ErrorColor = Color(0xFFBA1A1A)

// 🖋 Tipografía
val AppTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 12.sp
    )
)

// 🔺 Formas
val AppShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp)
)

// 🎨 Definición de esquemas de color
private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = PrimaryDarkColor,
    secondary = SecondaryColor,
    onSecondary = SecondaryDarkColor,
    error = ErrorColor
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDarkColor,
    onPrimary = PrimaryLightColor,
    secondary = SecondaryDarkColor,
    onSecondary = SecondaryColor,
    error = ErrorColor
)

// 🌙 ☀️ Tema dinámico con soporte para Material3 y colores adaptativos en Android 12+
@Composable
fun FindProTheme(
    useDynamicColors: Boolean = true,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDynamicColors && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else {
        if (darkTheme) DarkColorScheme else LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
