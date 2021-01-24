package com.astar.osterrig.controllightmvvm.model.repository

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.LiveData
import com.astar.osterrig.controllightmvvm.model.data.AppState
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

interface DeviceModelRepository {

    suspend fun getDevices(): List<DeviceModel>
    suspend fun getDeviceByMacAddress(macAddress: String): DeviceModel?
    suspend fun addDevice(device: DeviceModel)
    suspend fun deleteDevice(device: DeviceModel)
    suspend fun getGroups(): List<String>
    suspend fun getGroupsBySaberType(saberType: Int): List<String>
    suspend fun getDeviceFromGroup(groupName: String): List<DeviceModel>
    suspend fun createGroup(nameGroup: String, sabers: List<DeviceModel>)
    suspend fun renameDevice(newDevice: DeviceModel)
    suspend fun removeGroup(nameGroup: String)
    suspend fun renameGroup(oldGroupName: String, newGroupName: String)
    suspend fun addSaberToGroup(newDevice: DeviceModel)
    fun searchDevices(): Flow<List<BluetoothDevice>>
    fun scanState(): Flow<Boolean>
    fun getSearchState(): Boolean
    fun startScan()
    fun stopScan()

}