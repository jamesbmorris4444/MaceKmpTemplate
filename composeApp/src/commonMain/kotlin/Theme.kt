package com.mace.mace_template.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily

expect val colors: Colors

expect val avenirFontFamilyRegular: FontFamily
expect val avenirFontFamilyBold: FontFamily
expect val avenirFontFamilyMedium: FontFamily

expect val shapes: Shapes

expect val typography: Typography

expect val darkColorPalette: Colors

expect val lightColorPalette: Colors

@Composable
expect fun MaceTemplateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
)