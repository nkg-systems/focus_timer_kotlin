package com.example.gh0st_focus.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ==== Accents (unchanged vibe, a bit richer) ====
private val Violet = Color(0xFF7C4DFF)      // primary
private val VioletBright = Color(0xFF9E8CFF)
private val SkyBlue = Color(0xFF40C4FF)     // secondary
private val Teal = Color(0xFF00BFA5)        // tertiary accent if needed

// ==== Deep Dark palette ====
private val DarkBackground = Color(0xFF0B0D10)     // app background
private val DarkSurface    = Color(0xFF111318)     // cards, sheets
private val DarkSurfaceHi  = Color(0xFF171A21)     // elevated surfaces
private val OnDarkPrimary  = Color(0xFFFFFFFF)
private val OnDark         = Color(0xFFECEFF4)     // high-contrast text
private val OnDarkMuted    = Color(0xFFB8C1CC)     // secondary text
private val OutlineDark    = Color(0xFF2A2F36)     // dividers/borders

// ==== AMOLED variant (pure black bg) ====
private val AmoledBackground = Color(0xFF000000)
private val AmoledSurface    = Color(0xFF0A0A0A)
private val AmoledSurfaceHi  = Color(0xFF121212)

// Full dark color scheme
private val DarkColors = darkColorScheme(
    primary = Violet,
    onPrimary = OnDarkPrimary,
    primaryContainer = Violet.copy(alpha = 0.18f),
    onPrimaryContainer = OnDark,

    secondary = SkyBlue,
    onSecondary = Color.Black,
    secondaryContainer = SkyBlue.copy(alpha = 0.18f),
    onSecondaryContainer = OnDark,

    tertiary = Teal,
    onTertiary = Color.Black,
    tertiaryContainer = Teal.copy(alpha = 0.18f),
    onTertiaryContainer = OnDark,

    background = DarkBackground,
    onBackground = OnDark,

    surface = DarkSurface,
    onSurface = OnDark,
    surfaceVariant = DarkSurfaceHi,
    onSurfaceVariant = OnDarkMuted,

    error = Color(0xFFFF6E6E),
    onError = Color.Black,
    outline = OutlineDark
)

// AMOLED (pure black) variant
private val AmoledDarkColors = darkColorScheme(
    primary = Violet,
    onPrimary = OnDarkPrimary,
    primaryContainer = Violet.copy(alpha = 0.20f),
    onPrimaryContainer = OnDark,

    secondary = SkyBlue,
    onSecondary = Color.Black,
    secondaryContainer = SkyBlue.copy(alpha = 0.20f),
    onSecondaryContainer = OnDark,

    tertiary = Teal,
    onTertiary = Color.Black,
    tertiaryContainer = Teal.copy(alpha = 0.20f),
    onTertiaryContainer = OnDark,

    background = AmoledBackground,
    onBackground = OnDark,

    surface = AmoledSurface,
    onSurface = OnDark,
    surfaceVariant = AmoledSurfaceHi,
    onSurfaceVariant = OnDarkMuted,

    error = Color(0xFFFF6E6E),
    onError = Color.Black,
    outline = OutlineDark
)

// Keeping a light scheme around (won't be used unless you opt in)
private val LightColors = lightColorScheme(
    primary = Violet,
    onPrimary = Color.White,
    secondary = SkyBlue,
    onSecondary = Color.Black,
    background = Color(0xFFF6F7FB),
    onBackground = Color(0xFF121212),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1E222A),
    surfaceVariant = Color(0xFFF0F2F7),
    onSurfaceVariant = Color(0xFF4A5565),
    error = Color(0xFFB00020),
    onError = Color.White,
    outline = Color(0xFFE2E6EF)
)

/**
 * App theme.
 *
 * @param useDarkTheme Force dark; default is true so the app is always dark.
 * @param amoled If true, use pure #000000 background (great for OLED).
 */
@Composable
fun Gh0stFocusTheme(
    useDarkTheme: Boolean = true, // force dark by default
    amoled: Boolean = false,
    content: @Composable () -> Unit
) {
    val dark = if (useDarkTheme) {
        if (amoled) AmoledDarkColors else DarkColors
    } else {
        // If you ever want to allow light theme again
        LightColors
    }

    MaterialTheme(
        colorScheme = dark,
        typography = MaterialTheme.typography,
        content = content
    )
}
