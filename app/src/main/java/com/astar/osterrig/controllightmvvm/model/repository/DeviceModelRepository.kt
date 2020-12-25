package com.astar.osterrig.controllightmvvm.model.repository

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.LiveData
import com.astar.osterrig.controllightmvvm.model.data.AppState
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

interface DeviceModelRepository {

    fun getDevices(): LiveData<List<DeviceModel>>
    suspend fun getDeviceByMacAddress(macAddress: String): DeviceModel?
    suspend fun addDevice(device: DeviceModel)
    suspend fun deleteDevice(device: DeviceModel)
    suspend fun getGroups(): List<String>
    suspend fun getDeviceFromGroup(groupName: String): List<DeviceModel>

    fun searchDevices(): Flow<List<BluetoothDevice>>
    fun getSearchState(): Boolean
    fun startScan()
    fun stopScan()
}