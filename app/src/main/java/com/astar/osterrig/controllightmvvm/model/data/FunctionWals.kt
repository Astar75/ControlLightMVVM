package com.astar.osterrig.controllightmvvm.model.data

import android.graphics.Color

/**
 * Представляет собой эффект для адрессной лампы
 */
data class FunctionWals(
    var code: Int,                                 // код эффекта
    var name: String,                              // название эффекта
    var icon: Int,                                 // иконка
    var useColors: Int,                            // кол-во используемых цветов
    var colorArray: IntArray,                      // массив цветов
    var isSmooth: Boolean,                         // эффект размытия
    var isReverse: Boolean,                        // развернут ли эффект
    var cooling: Int,
    var sparking: Int,
    var speed: Int,                                // скорость эффекта
    var lightness: Int                             // яркость эффекта
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FunctionWals

        if (code != other.code) return false
        if (name != other.name) return false
        if (icon != other.icon) return false
        if (useColors != other.useColors) return false
        if (!colorArray.contentEquals(other.colorArray)) return false
        if (isSmooth != other.isSmooth) return false
        if (isReverse != other.isReverse) return false
        if (speed != other.speed) return false
        if (lightness != other.lightness) return false

        return true
    }

    override fun hashCode(): Int {
        var result = code
        result = 31 * result + name.hashCode()
        result = 31 * result + icon
        result = 31 * result + useColors
        result = 31 * result + colorArray.contentHashCode()
        result = 31 * result + isSmooth.hashCode()
        result = 31 * result + isReverse.hashCode()
        result = 31 * result + speed
        result = 31 * result + lightness
        return result
    }
}