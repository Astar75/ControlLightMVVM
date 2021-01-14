package com.astar.osterrig.controllightmvvm.model.datasource.persistence

import androidx.lifecycle.LiveData
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel

interface DeviceModelDataSource {

    suspend fun getDevices(): List<DeviceModel>
    suspend fun getDeviceByMacAddress(macAddress: String): DeviceModel?
    suspend fun addDevice(device: DeviceModel)
    suspend fun deleteDevice(device: DeviceModel)
    suspend fun updateDevice(device: DeviceModel)
    suspend fun getGroups(): List<String>
    suspend fun getDeviceFromGroup(groupName: String): List<DeviceModel>
}