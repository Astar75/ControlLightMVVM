package com.astar.osterrig.controllightmvvm.utils

import android.content.res.ColorStateList
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun View.setBackgroundTint(@ColorRes colorRes: Int) {
    this.backgroundTintList =
        ColorStateList.valueOf(ContextCompat.getColor(this.context, colorRes))
}