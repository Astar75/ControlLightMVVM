package com.astar.osterrig.controllightmvvm.model.data

import androidx.annotation.ColorInt

data class CctColorEntity(
    val kelvin: String,
    @ColorInt val backgroundCell: Int,
    val red: Int,
    val green: Int,
    val blue: Int,
    val white: Int
)