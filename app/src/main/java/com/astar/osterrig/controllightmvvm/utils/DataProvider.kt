package com.astar.osterrig.controllightmvvm.utils

import android.graphics.Color
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.model.data.CctColorEntity
import com.astar.osterrig.controllightmvvm.model.data.FunctionRgb
import com.astar.osterrig.controllightmvvm.model.data.FunctionWals

object DataProvider {

    fun getCctColorList(): List<CctColorEntity> = listOf(
        CctColorEntity("8000K", Color.rgb(221, 230, 255), 27, 102, 150, 255),
        CctColorEntity("7000K", Color.rgb(243, 242, 255), 27, 75, 102, 255),
        CctColorEntity("6200K", Color.rgb(255, 249, 242), 48, 96, 67, 255),
        CctColorEntity("5600K", Color.rgb(255, 239, 225), 59, 62, 31, 255),
        CctColorEntity("4500K", Color.rgb(255, 218, 187), 132, 50, 0, 255),
        CctColorEntity("3200K", Color.rgb(255, 184, 123), 255, 98, 0, 85),
    )

    /**
     * Первый элемент - код эффекта для включения
     * Второй элемент - название эффекта
     * Третий элемент - иконка эффекта
     */
    fun getWalsFunctionNameList(): List<Triple<Int, String, Int>> = listOf(
        Triple(0, "None", R.drawable.ic_btn_no_color),
        Triple(102, "HSV", R.drawable.ic_func_hsv),
        Triple(103, "Cylon", R.drawable.ic_func_cylon),
        Triple(104, "Flashlight", R.drawable.ic_func_flashlight),
        Triple(105, "Fire", R.drawable.ic_func_fire),
        Triple(114, "Pride", R.drawable.ic_func_pride),
        Triple(132, "Fade/Strobe", R.drawable.ic_func_fadestrobe),
        Triple(128, "Broken lamp", R.drawable.ic_func_broken_lamp),
        Triple(133, "Snake", R.drawable.ic_func_snake)
    )

    fun getRgbFunctionNameList(): List<Triple<Int, String, Int>> = listOf(
        Triple(0, "None", R.drawable.ic_btn_no_color),
        Triple(102, "HSV", R.drawable.ic_func_hsv),
        Triple(103, "Cylon", R.drawable.ic_func_cylon),
        Triple(104, "Flashlight", R.drawable.ic_func_flashlight),
        Triple(105, "Fire", R.drawable.ic_func_fire),
        Triple(114, "Pride", R.drawable.ic_func_pride),
        Triple(132, "Fade/Strobe", R.drawable.ic_func_fadestrobe),
        Triple(128, "Broken lamp", R.drawable.ic_func_broken_lamp),
        Triple(133, "Snake", R.drawable.ic_func_snake)
    )

    fun getRgbFunctions() = arrayOf(
        FunctionRgb(0, "None", R.drawable.ic_btn_no_color, 255, 1),
        FunctionRgb(100, "None", R.drawable.ic_btn_no_color, 255, 1),
        FunctionRgb(101, "None", R.drawable.ic_btn_no_color, 255, 1),
        FunctionRgb(102, "None", R.drawable.ic_btn_no_color, 255, 1),
        FunctionRgb(103, "None", R.drawable.ic_btn_no_color, 255, 1),
        FunctionRgb(104, "None", R.drawable.ic_btn_no_color, 255, 1),
        FunctionRgb(105, "None", R.drawable.ic_btn_no_color, 255, 1),
        FunctionRgb(106, "None", R.drawable.ic_btn_no_color, 255, 1),
        FunctionRgb(107, "None", R.drawable.ic_btn_no_color, 255, 1)
    )

    fun getWalsFunctions() = arrayOf(
        FunctionWals(0, "None", R.drawable.ic_btn_no_color, 2, IntArray(8), true, false, 1, 255),
        FunctionWals(0, "None", R.drawable.ic_btn_no_color, 2, IntArray(8), true, false, 1, 255),
        FunctionWals(0, "None", R.drawable.ic_btn_no_color, 2, IntArray(8), true, false, 1, 255),
        FunctionWals(0, "None", R.drawable.ic_btn_no_color, 2, IntArray(8), true, false, 1, 255),
        FunctionWals(0, "None", R.drawable.ic_btn_no_color, 2, IntArray(8), true, false, 1, 255),
        FunctionWals(0, "None", R.drawable.ic_btn_no_color, 2, IntArray(8), true, false, 1, 255),
        FunctionWals(0, "None", R.drawable.ic_btn_no_color, 2, IntArray(8), true, false, 1, 255),
        FunctionWals(0, "None", R.drawable.ic_btn_no_color, 2, IntArray(8), true, false, 1, 255),
        FunctionWals(0, "None", R.drawable.ic_btn_no_color, 2, IntArray(8), true, false, 1, 255)
    )
}