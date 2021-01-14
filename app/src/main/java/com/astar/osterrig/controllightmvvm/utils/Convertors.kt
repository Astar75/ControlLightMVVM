package com.astar.osterrig.controllightmvvm.utils

import android.bluetooth.BluetoothDevice
import android.graphics.Color
import androidx.annotation.ColorInt
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel

fun convertingBluetoothDeviceToDeviceModel(
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