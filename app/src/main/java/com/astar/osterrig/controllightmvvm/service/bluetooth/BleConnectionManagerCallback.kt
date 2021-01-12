package com.astar.osterrig.controllightmvvm.service.bluetooth

import android.bluetooth.BluetoothDevice

interface BleConnectionManagerCallback {

    fun onBatteryValue(bluetoothDevice: BluetoothDevice, batteryValue: Int)
    fun onSaberCurrentColor(bluetoothDevice: BluetoothDevice, colorStr: String)
    fun onCurrentLightness(bluetoothDevice: BluetoothDevice, lightness: Int)
    fun onConnected(bluetoothDevice: BluetoothDevice)
    fun onDisconnected(bluetoothDevice: BluetoothDevice)
    fun onConnecting(bluetoothDevice: BluetoothDevice)
    fun onFailedToConnect(bluetoothDevice: BluetoothDevice)
    fun isConnected(device: BluetoothDevice)
}