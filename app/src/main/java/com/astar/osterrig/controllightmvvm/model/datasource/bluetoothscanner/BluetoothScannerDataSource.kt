package com.astar.osterrig.controllightmvvm.model.datasource.bluetoothscanner

import android.bluetooth.BluetoothDevice
import kotlinx.coroutines.flow.Flow

interface BluetoothScannerDataSource {

    fun searchDevices(): Flow<List<BluetoothDevice>>
    fun getScanningState(): Boolean
    fun startScan()
    fun stopScan()
    fun scanState(): Flow<Boolean>
}