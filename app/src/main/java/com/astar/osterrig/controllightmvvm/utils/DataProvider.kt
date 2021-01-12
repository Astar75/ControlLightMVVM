package com.astar.osterrig.controllightmvvm.utils

import android.graphics.Color
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.model.data.CctColorEntity
import com.astar.osterrig.controllightmvvm.model.data.FunctionRgb
import com.astar.osterrig.controllightmvvm.model.data.FunctionWals
import com.astar.osterrig.controllightmvvm.utils.Constants.*

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
    fun getWalsFunctionNameList(): ArrayList<Triple<Int, String, Int>> = arrayListOf(
        Triple(WalsFunctionCode.NONE, "None", R.drawable.ic_btn_no_color),
        Triple(WalsFunctionCode.HSV, "HSV", R.drawable.ic_func_hsv),
        Triple(WalsFunctionCode.CYLON, "Cylon", R.drawable.ic_func_cylon),
        Triple(WalsFunctionCode.FLASH_LIGHT, "Flashlight", R.drawable.ic_func_flashlight),
        Triple(WalsFunctionCode.FIRE, "Fire", R.drawable.ic_func_fire),
        Triple(WalsFunctionCode.PRIDE, "Pride", R.drawable.ic_func_pride),
        Triple(WalsFunctionCode.FADE, "Fade/Strobe", R.drawable.ic_func_fadestrobe),
        Triple(WalsFunctionCode.BROKEN_LAMP, "Broken lamp", R.drawable.ic_func_broken_lamp),
        Triple(WalsFunctionCode.SNAKE, "Snake", R.drawable.ic_func_snake)
    )

    fun getRgbFunctionNameList(): ArrayList<Triple<Int, String, Int>> = arrayListOf(
        Triple(RgbFunctionCode.NONE, "None", R.drawable.ic_btn_no_color),
        Triple(RgbFunctionCode.CROSS_FADE, "Cross fade", R.drawable.ic_func_rgb_crossfade),
        Triple(RgbFunctionCode.RED_FADE, "Red fade", R.drawable.ic_func_rgb_red_fade),
        Triple(RgbFunctionCode.GREEN_FADE, "Green fade", R.drawable.ic_func_rgb_green_fade),
        Triple(RgbFunctionCode.BLUE_FADE, "Blue fade", R.drawable.ic_func_rgb_blue_fade),
        Triple(RgbFunctionCode.YELLOW_FADE, "Yellow fade", R.drawable.ic_func_rgb_yellow_fade),
        Triple(RgbFunctionCode.PURPLE_FADE, "Purple fade", R.drawable.ic_func_rgb_purple_fade),
        Triple(RgbFunctionCode.CYAN_FADE, "Cyan fade", R.drawable.ic_func_rgb_cyan_fade),
        Triple(RgbFunctionCode.RED_BLUE_FADE, "Red blue fade", R.drawable.ic_func_rgb_red_blue_fade),
        Triple(RgbFunctionCode.RED_GREEN_FADE, "Red green fade", R.drawable.ic_func_rgb_red_green_fade),
        Triple(RgbFunctionCode.GREEN_BLUE_FADE, "Green blue fade", R.drawable.ic_func_rgb_green_blue_fade),
        Triple(RgbFunctionCode.POLICE, "Police", R.drawable.ic_func_rgb_police),
        Triple(RgbFunctionCode.STROBE_FLASH, "Strobe flash", R.drawable.ic_func_snake),
        Triple(RgbFunctionCode.RED_STROBE_FLASH, "Red strobe flash", R.drawable.ic_func_rgb_red_strobe),
        Triple(RgbFunctionCode.GREEN_STROBE_FLASH, "Green strobe flash", R.drawable.ic_func_rgb_green_strobe),
        Triple(RgbFunctionCode.BLUE_STROBE_FLASH, "Blue strobe flash", R.drawable.ic_func_rgb_blue_strobe),
        Triple(RgbFunctionCode.YELLOW_STROBE_FLASH, "Yellow strobe flash", R.drawable.ic_func_rgb_yellow_strobe),
        Triple(RgbFunctionCode.PURPLE_STROBE_FLASH, "Purple strobe flash", R.drawable.ic_func_rgb_purple_strobe),
        Triple(RgbFunctionCode.CYAN_STROBE_FLASH, "Cyan strobe flash", R.drawable.ic_func_rgb_cyan_strobe),
        Triple(RgbFunctionCode.JUMPING_CHANGE, "Jumping change", R.drawable.ic_func_snake),
        Triple(RgbFunctionCode.BROKEN_LAMP_1, "Broken lamp 1", R.drawable.ic_func_rgb_broken_lamp_1),
        Triple(RgbFunctionCode.BROKEN_LAMP_2, "Broken lamp 2", R.drawable.ic_func_rgb_broken_lamp_2),
        Triple(RgbFunctionCode.BROKEN_LAMP_3, "Broken lamp 3", R.drawable.ic_func_rgb_broken_lamp_3),
        Triple(RgbFunctionCode.BROKEN_LAMP_4, "Broken lamp 4", R.drawable.ic_func_rgb_broken_lamp_4),
        Triple(RgbFunctionCode.BROKEN_LAMP_5, "Broken lamp 5", R.drawable.ic_func_rgb_broken_lamp_5)
    )

    fun getPresetRgbFunctions() = arrayOf(
        FunctionRgb(0, "None", R.drawable.ic_btn_no_color, 255, 1),
        FunctionRgb(0, "None", R.drawable.ic_btn_no_color, 255, 1),
        FunctionRgb(0, "None", R.drawable.ic_btn_no_color, 255, 1),
        FunctionRgb(0, "None", R.drawable.ic_btn_no_color, 255, 1),
        FunctionRgb(0, "None", R.drawable.ic_btn_no_color, 255, 1),
        FunctionRgb(0, "None", R.drawable.ic_btn_no_color, 255, 1),
        FunctionRgb(0, "None", R.drawable.ic_btn_no_color, 255, 1),
        FunctionRgb(0, "None", R.drawable.ic_btn_no_color, 255, 1),
        FunctionRgb(0, "None", R.drawable.ic_btn_no_color, 255, 1)
    )

    fun getPresetWalsFunctions() = arrayOf(
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