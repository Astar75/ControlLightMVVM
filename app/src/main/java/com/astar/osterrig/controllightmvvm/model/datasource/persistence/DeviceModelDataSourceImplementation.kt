package com.astar.osterrig.controllightmvvm.model.datasource.persistence

import androidx.lifecycle.LiveData
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel

internal class DeviceModelDataSourceImplementation(private val appDatabase: AppDatabase) : DeviceModelDataSource {

    override fun getDevices(): LiveData<List<DeviceModel>> {
        return appDatabase.getDevicesDao().getDevices()
    }

    override suspend fun getDeviceByMacAddress(macAddress: String): DeviceModel? {
        return appDatabase.getDevicesDao().getDeviceByMacAddress(macAddress)
    }

    override suspend fun addDevice(device: DeviceModel) {
        appDatabase.getDevicesDao().insertDevice(device)
    }

    override suspend fun deleteDevice(device: DeviceModel) {
        appDatabase.getDevicesDao().deleteDevice(device)
    }

    override suspend fun getGroups(): List<String> = appDatabase.getDevicesDao().getGroups()

    override suspend fun getDeviceFromGroup(groupName: String): List<DeviceModel> =
        appDatabase.getDevicesDao().getDevicesFromGroup(groupName)
}