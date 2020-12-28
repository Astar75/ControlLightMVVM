package com.astar.osterrig.controllightmvvm.utils

import android.graphics.Color
import com.astar.osterrig.controllightmvvm.model.data.CctColorEntity

object DataProvider {
    fun getCctColorList(): List<CctColorEntity> = listOf(
        CctColorEntity("8000K", Color.rgb(221, 230, 255), 27, 102, 150, 255),
        CctColorEntity("7000K", Color.rgb(243, 242, 255), 27, 75, 102, 255),
        CctColorEntity("6200K", Color.rgb(255, 249, 242), 48, 96, 67, 255),
        CctColorEntity("5600K", Color.rgb(255, 239, 225), 59, 62, 31, 255),
        CctColorEntity("4500K", Color.rgb(255, 218, 187), 132, 50, 0, 255),
        CctColorEntity("3200K", Color.rgb(255, 184, 123), 255, 98, 0, 85),
    )
}