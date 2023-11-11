package com.mace.mace_template.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.ajalt.colormath.extensions.android.composecolor.toComposeColor
import com.github.ajalt.colormath.model.RGB
import com.mace.kmptemplate.R

private val purple000 = RGB("#eea6fc").toComposeColor()
private val purple200 = RGB("#bb86fc").toComposeColor()
private val purple500 = RGB("#6200ee").toComposeColor()
private val purple700 = RGB("#3700b3").toComposeColor()
private val black = RGB("#3700b3").toComposeColor()
private val white = RGB("#3700b3").toComposeColor()
private val red = RGB("#3700b3").toComposeColor()
private val green = RGB("#3700b3").toComposeColor()

actual val colors = Colors(
    primary = purple700,
    primaryVariant = purple500,
    secondary = purple200,
    secondaryVariant = green,
    background = white,
    surface = white,
    error = red,
    onPrimary = white,
    onSecondary = white,
    onBackground = black,
    onSurface = black,
    onError = white,
    isLight = false
)

actual val avenirFontFamilyRegular: FontFamily = FontFamily(Font(R.font.avenir_regular))
actual val avenirFontFamilyBold: FontFamily = FontFamily(Font(R.font.avenir_bold, FontWeight.Bold))
actual val avenirFontFamilyMedium: FontFamily = FontFamily(Font(R.font.avenir_book))

actual val shapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp)
)

actual val typography = Typography(
    h1 = TextStyle(fontFamily = avenirFontFamilyRegular),
    h2 = TextStyle(fontFamily = avenirFontFamilyRegular),
    h3 = TextStyle(fontFamily = avenirFontFamilyRegular),
    h4 = TextStyle(fontFamily = avenirFontFamilyRegular),
    h5 = TextStyle(fontFamily = avenirFontFamilyRegular),
    h6 = TextStyle(fontFamily = avenirFontFamilyRegular),
    body1 = TextStyle(fontFamily = avenirFontFamilyRegular),
    body2 = TextStyle(fontFamily = avenirFontFamilyRegular),
    subtitle1 = TextStyle(fontFamily = avenirFontFamilyRegular),
    subtitle2 = TextStyle(fontFamily = avenirFontFamilyRegular),
    button = TextStyle(fontFamily = avenirFontFamilyRegular),
    caption = TextStyle(fontFamily = avenirFontFamilyRegular),
    overline = TextStyle(fontFamily = avenirFontFamilyRegular)
)

actual val darkColorPalette = darkColors(
    primary = colors.secondary,
    primaryVariant = colors.primaryVariant,
    secondary = colors.primary
)

actual val lightColorPalette = lightColors(
    primary = colors.primary,
    primaryVariant =  colors.primaryVariant,
    secondary = colors.secondary
)

@Composable
actual fun MaceTemplateTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorPalette
    } else {
        lightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}