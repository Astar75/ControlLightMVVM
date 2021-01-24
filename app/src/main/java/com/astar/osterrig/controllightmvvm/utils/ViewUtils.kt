package com.astar.osterrig.controllightmvvm.utils

import android.content.res.ColorStateList
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat

fun View.setColorPreviewBackground(@ColorInt color: Int) {
    val newBackground = this.background.mutate()
    newBackground.colorFilter =
        BlendModeColorFilterCompat.createBlendModeColorFilterCompat(color, BlendModeCompat.SRC_ATOP)
    this.background = newBackground
}

fun TextView.setColorPreviewBackground(@ColorInt color: Int) {
    val newBackground = this.background.mutate()
    newBackground.colorFilter =
        BlendModeColorFilterCompat.createBlendModeColorFilterCompat(color, BlendModeCompat.SRC_ATOP)
    this.background = newBackground
}

fun View.setBackgroundTint(@ColorRes colorRes: Int) {
    this.backgroundTintList =
        ColorStateList.valueOf(ContextCompat.getColor(this.context, colorRes))
}

fun TextView.toPercentValue(value: Int, max: Int = 255, formattedText: String? = null) {
    if (formattedText != null) {
        this.text = String.format(formattedText, (value * 100 / max))
    } else {
        this.text = (value * 100 / max).toString()
    }
}