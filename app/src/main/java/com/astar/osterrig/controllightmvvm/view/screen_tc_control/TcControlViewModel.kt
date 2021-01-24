package com.astar.osterrig.controllightmvvm.view.screen_tc_control

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astar.osterrig.controllightmvvm.utils.tmp.ColorConverter

class TcControlViewModel: ViewModel() {

    private val _lightnessLiveData: MutableLiveData<Int> = MutableLiveData(0)
    val lightnessLiveData: LiveData<Int>
        get() = _lightnessLiveData

    private val _temperatureLiveData: MutableLiveData<Triple<Int, Int, Int>> = MutableLiveData()
    val temperatureLiveData: LiveData<Triple<Int, Int, Int>>
        get() = _temperatureLiveData

    private val _temperaturePreviewLiveData: MutableLiveData<Int> = MutableLiveData()
    val temperaturePreviewLiveData: LiveData<Int>
        get() = _temperaturePreviewLiveData


    private val listTemperature = listOf(
        Triple(3200, 0, 255),
        Triple(3225, 5, 255),
        Triple(3250, 8, 255),
        Triple(3275, 10, 255),
        Triple(3300, 13, 255),
        Triple(3325, 15, 255),
        Triple(3300, 18, 255),
        Triple(3375, 20, 255),
        Triple(3400, 23, 255),
        Triple(3425, 26, 255),
        Triple(3450, 28, 255),
        Triple(3475, 31, 255),
        Triple(3500, 33, 255),
        Triple(3525, 36, 255),
        Triple(3550, 38, 255),
        Triple(3575, 41, 255),
        Triple(3600, 43, 255),
        Triple(3625, 46, 255),
        Triple(3650, 48, 255),
        Triple(3675, 51, 255),
        Triple(3700, 54, 255),
        Triple(3725, 56, 255),
        Triple(3750, 59, 255),
        Triple(3775, 61, 255),
        Triple(3800, 64, 255),
        Triple(3825, 66, 255),
        Triple(3850, 69, 255),
        Triple(3875, 71, 255),
        Triple(3900, 74, 255),
        Triple(3925, 77, 255),
        Triple(3950, 79, 255),
        Triple(3975, 82, 255),
        Triple(4000, 84, 255),
        Triple(4025, 87, 255),
        Triple(4050, 89, 255),
        Triple(4075, 92, 255),
        Triple(4100, 94, 255),
        Triple(4125, 97, 255),
        Triple(4150, 99, 255),
        Triple(4175, 102, 255),
        Triple(4200, 105, 255),
        Triple(4225, 107, 255),
        Triple(4250, 110, 255),
        Triple(4275, 112, 255),
        Triple(4300, 115, 255),
        Triple(4325, 117, 255),
        Triple(4350, 120, 255),
        Triple(4375, 122, 255),
        Triple(4400, 125, 255),
        Triple(4425, 128, 255),
        Triple(4450, 130, 250),
        Triple(4475, 133, 245),
        Triple(4500, 135, 240),
        Triple(4525, 138, 235),
        Triple(4550, 140, 230),
        Triple(4575, 143, 224),
        Triple(4600, 145, 219),
        Triple(4625, 148, 214),
        Triple(4650, 150, 209),
        Triple(4675, 153, 204),
        Triple(4700, 156, 199),
        Triple(4725, 158, 194),
        Triple(4750, 161, 189),
        Triple(4775, 163, 184),
        Triple(4800, 166, 179),
        Triple(4825, 168, 173),
        Triple(4850, 171, 168),
        Triple(4875, 173, 163),
        Triple(4900, 176, 158),
        Triple(4925, 179, 153),
        Triple(4950, 181, 148),
        Triple(4975, 184, 143),
        Triple(5000, 186, 138),
        Triple(5025, 189, 133),
        Triple(5050, 191, 128),
        Triple(5075, 194, 122),
        Triple(5100, 196, 117),
        Triple(5125, 199, 112),
        Triple(5150, 201, 107),
        Triple(5175, 204, 102),
        Triple(5200, 207, 97),
        Triple(5225, 209, 92),
        Triple(5250, 212, 87),
        Triple(5275, 214, 82),
        Triple(5300, 217, 77),
        Triple(5325, 219, 71),
        Triple(5350, 222, 66),
        Triple(5375, 224, 61),
        Triple(5400, 227, 56),
        Triple(5425, 230, 51),
        Triple(5450, 232, 46),
        Triple(5475, 235, 41),
        Triple(5500, 237, 36),
        Triple(5575, 240, 31),
        Triple(5600, 242, 26),
        Triple(5625, 245, 20),
        Triple(5650, 247, 15),
        Triple(5675, 250, 10),
        Triple(5700, 252, 0),
    )

    fun setTemperature(value: Int) {
        val kalvin = ColorConverter.kelvinToRgb(listTemperature[value].first.toFloat())
        _temperatureLiveData.value = listTemperature[value]
        _temperaturePreviewLiveData.value = Color.rgb(
            kalvin.red, kalvin.green, kalvin.blue
        )
    }

    fun setLightness(lightness: Int) {
        _lightnessLiveData.value = lightness
    }
}