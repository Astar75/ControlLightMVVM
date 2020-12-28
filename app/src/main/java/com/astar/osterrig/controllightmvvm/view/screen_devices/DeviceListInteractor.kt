package com.astar.osterrig.controllightmvvm.view.screen_devices

import android.bluetooth.BluetoothDevice
import kotlinx.coroutines.flow.Flow

interface DeviceListInteractor {

    fun startScan()
    fun searchDevicesFlow(): Flow<List<BluetoothDevice>>
    fun getSearchState(): Boolean
}