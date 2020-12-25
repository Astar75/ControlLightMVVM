package com.astar.osterrig.controllightmvvm.utils

import android.bluetooth.BluetoothDevice
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel

fun convertingBluetoothDeviceToDeviceModel(source : List<BluetoothDevice>): List<DeviceModel> {
    return source.map {
        DeviceModel(it.address, it.name, "")
    }
}