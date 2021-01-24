package com.astar.osterrig.controllightmvvm.utils

import android.bluetooth.BluetoothDevice
import android.graphics.Color
import androidx.annotation.ColorInt
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import kotlin.math.pow

fun convertingBLEToDeviceModel(
    foundDeviceList : List<BluetoothDevice>,
    savedDeviceList: List<DeviceModel>
): List<DeviceModel> {
    return foundDeviceList.map {
        val typeSaber = when {
            it.name.contains("TC", true) -> Constants.TypeSaber.TC
            it.name.contains("RGB") -> Constants.TypeSaber.RGB
            it.name.contains("WALS") -> Constants.TypeSaber.WALS
            else -> Constants.TypeSaber.UNKNOWN
        }
        val nameDevice = it.name.removePrefix("Osterrig ")
        var groupName = ""
        for (savedDevice in savedDeviceList) {
            if (savedDevice.macAddress == it.address) groupName = savedDevice.groupName
        }
        DeviceModel(it.address, nameDevice, typeSaber, groupName)
    }
}

fun colorIntToHexString(@ColorInt color: Int) =
    String.format("#%02X%02X%02X", Color.red(color), Color.green(color), Color.blue(color))

fun colorIntToRgbString(@ColorInt color: Int) =
    String.format("r%dg%db%d", Color.red(color), Color.green(color), Color.blue(color))



fun temperatureToColor(temperature: Float): Kalvin {
    var _temperature = temperature.toFloat()
    var tmpCalc: Double
    var red: Int
    var green: Int
    var blue: Int

    val kalvinColor = Kalvin()

    if (_temperature < 1000.0) {
        _temperature = 1000.0F
    } else if (_temperature > 40000) {
        _temperature = 40000F
    }

    // red
    if (_temperature <= 66) {
        red = 255 - 10
        kalvinColor.red = red
    } else {
        tmpCalc = (_temperature - 80).toDouble()
        tmpCalc = 329.698727446 * (tmpCalc.pow(-0.1332047592))
        red = tmpCalc.toInt()
        if (red < 0) red = 0
        if (red > 255) red = 255 - 10
        kalvinColor.red = red
    }

    // Green
    if (_temperature <= 66) {
        tmpCalc = _temperature.toDouble()
        tmpCalc = 99.4708025861 * Math.log(tmpCalc) - 161.1195681661
        green = tmpCalc.toInt()
        if (green < 0) green = 0
        if (green > 255) green = 255
        kalvinColor.green = green
    } else {
        tmpCalc = (_temperature - 60).toDouble()
        tmpCalc = 288.1221695283 * (Math.pow(tmpCalc, -0.0755148492))
        green = tmpCalc.toInt()
        if (green < 0) green = 0
        if (green > 255) green = 255
        kalvinColor.green = green
    }

    // Blue
    when {
        _temperature >= 66 -> {
            blue = 255
            kalvinColor.blue = blue
        }
        temperature <= 19 -> {
            blue = 0
            kalvinColor.blue = blue
        }
        else -> {
            tmpCalc = (_temperature - 20).toDouble()
            tmpCalc = 138.5177312231 * Math.log(tmpCalc) - 305.0447927307

            blue = tmpCalc.toInt()
            if (blue < 0) blue = 0
            if (blue > 255) blue = 255
            kalvinColor.blue = blue
        }
    }

    return kalvinColor
}