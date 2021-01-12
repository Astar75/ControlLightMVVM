package com.astar.osterrig.controllightmvvm.service.bluetooth

import android.bluetooth.BluetoothDevice
import com.astar.osterrig.controllightmvvm.model.data.CctColorEntity

interface BleConnectionManager {

    fun addCallback(callback: BleConnectionManagerCallback)
    fun removeCallback()
    fun isConnected(device: BluetoothDevice?): Boolean
    fun connect(device: BluetoothDevice)
    fun disconnect(device: BluetoothDevice)
    fun disconnectAll()
    fun setLightness(device: BluetoothDevice, lightness: Int)
    fun setSpeed(device: BluetoothDevice, speed: Int)
    fun setColor(device: BluetoothDevice, color: Int)
    fun setColor(device: BluetoothDevice, colorModel: CctColorEntity)
    fun setFunction(device: BluetoothDevice?, typeSaber: Int, command: String)
}