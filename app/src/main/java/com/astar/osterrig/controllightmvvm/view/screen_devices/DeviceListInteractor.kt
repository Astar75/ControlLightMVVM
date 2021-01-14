package com.astar.osterrig.controllightmvvm.view.screen_devices

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.LiveData
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import kotlinx.coroutines.flow.Flow

interface DeviceListInteractor {

    fun startScan()
    fun searchDevicesFlow(): Flow<List<BluetoothDevice>>
    fun getSearchState(): Boolean
    fun scanState(): Flow<Boolean>

    suspend fun addDeviceToDatabase(deviceModel: DeviceModel)
    suspend fun getDevicesFromDatabase(): List<DeviceModel>
}