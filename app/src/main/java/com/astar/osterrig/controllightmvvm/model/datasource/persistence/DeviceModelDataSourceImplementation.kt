package com.astar.osterrig.controllightmvvm.model.datasource.persistence

import androidx.lifecycle.LiveData
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel

internal class DeviceModelDataSourceImplementation(private val appDatabase: AppDatabase) :
    DeviceModelDataSource {

    override suspend fun getDevices(): List<DeviceModel> {
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

    override suspend fun updateDevice(device: DeviceModel) {
        appDatabase.getDevicesDao().updateDevice(device)
    }

    override suspend fun renameGroup(oldGroupName: String, newGroupName: String) {
        appDatabase.getDevicesDao().renameGroup(oldGroupName, newGroupName)
    }

    override suspend fun removeGroup(nameGroup: String) {
        appDatabase.getDevicesDao().removeGroup(nameGroup)
    }

    override suspend fun getGroups(): List<String> = appDatabase.getDevicesDao().getGroups()

    override suspend fun getGroupsBySaberType(saberType: Int): List<String> =
        appDatabase.getDevicesDao().getGroupsBySaberType(saberType)

    override suspend fun getDeviceFromGroup(groupName: String): List<DeviceModel> =
        appDatabase.getDevicesDao().getDevicesFromGroup(groupName)
}