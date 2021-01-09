package com.astar.osterrig.controllightmvvm.utils

import android.bluetooth.BluetoothDevice
import android.graphics.Color
import androidx.annotation.ColorInt
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel

fun convertingBluetoothDeviceToDeviceModel(source : List<BluetoothDevice>): List<DeviceModel> {
    return source.map {
        val typeSaber = when {
            it.name.contains("TC", true) -> Constants.TypeSaber.TC
            it.name.contains("RGB") -> Constants.TypeSaber.RGB
            it.name.contains("WALS") -> Constants.TypeSaber.WALS
            else -> Constants.TypeSaber.UNKNOWN
        }
        val nameDevice = it.name.removePrefix("Osterrig ")
        DeviceModel(it.address, nameDevice, typeSaber, "")
    }
}

fun colorIntToHexString(@ColorInt color: Int) =
    String.format("#%02X%02X%02X", Color.red(color), Color.green(color), Color.blue(color))