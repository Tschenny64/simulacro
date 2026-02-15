package com.example.simulacro.modelo

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class Ruta(
    @StringRes val titulo: Int,
    val ruta: String,
    val iconoLleno: ImageVector,
    val iconoVacio: ImageVector
)
