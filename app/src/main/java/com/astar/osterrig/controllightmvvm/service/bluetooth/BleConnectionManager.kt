package com.astar.osterrig.controllightmvvm.service.bluetooth

import android.bluetooth.BluetoothDevice

interface BleConnectionManager {
    fun connect(device: BluetoothDevice)
    fun disconnect(device : BluetoothDevice)
    fun setLightness(device: BluetoothDevice, lightness: Int)
    fun setColor(device: BluetoothDevice, color: String)
}