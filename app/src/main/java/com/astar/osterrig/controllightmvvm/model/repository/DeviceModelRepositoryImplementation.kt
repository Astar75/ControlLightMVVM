package com.astar.osterrig.controllightmvvm.model.repository

import android.bluetooth.BluetoothDevice
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.model.datasource.bluetoothscanner.BluetoothScannerDataSource
import com.astar.osterrig.controllightmvvm.model.datasource.persistence.DeviceModelDataSource
import kotlinx.coroutines.flow.Flow

internal class DeviceModelRepositoryImplementation(
    private val dataSource: DeviceModelDataSource,
    private val bluetoothScannerDataSource: BluetoothScannerDataSource
) : DeviceModelRepository {

    override suspend fun getDevices(): List<DeviceModel> {
        return dataSource.getDevices()
    }

    override suspend fun getDeviceByMacAddress(macAddress: String): DeviceModel? {
        return dataSource.getDeviceByMacAddress(macAddress)
    }

    override suspend fun addDevice(device: DeviceModel) {
        dataSource.addDevice(device)
    }

    override suspend fun deleteDevice(device: DeviceModel) {
        dataSource.deleteDevice(device)
    }

    override suspend fun getGroups(): List<String> = dataSource.getGroups()

    override suspend fun getGroupsBySaberType(saberType: Int): List<String> =
        dataSource.getGroupsBySaberType(saberType)

    override suspend fun getDeviceFromGroup(groupName: String): List<DeviceModel> =
        dataSource.getDeviceFromGroup(groupName)

    override suspend fun createGroup(nameGroup: String, sabers: List<DeviceModel>) {
        for (saber in sabers) {
            val newSaber = DeviceModel(
                macAddress = saber.macAddress,
                name = saber.name,
                typeSaber = saber.typeSaber,
                groupName = nameGroup
            )
            dataSource.updateDevice(newSaber)
        }
    }

    override suspend fun addSaberToGroup(newDevice: DeviceModel) {
        dataSource.updateDevice(newDevice)
    }

    override suspend fun renameDevice(newDevice: DeviceModel) {
        dataSource.updateDevice(newDevice)
    }

    override suspend fun renameGroup(oldGroupName: String, newGroupName: String) {
        dataSource.renameGroup(oldGroupName, newGroupName)
    }

    override suspend fun removeGroup(nameGroup: String) {
        dataSource.removeGroup(nameGroup)
    }

    override fun startScan() {
        bluetoothScannerDataSource.startScan()
    }

    override fun stopScan() {
        bluetoothScannerDataSource.stopScan()
    }

    override fun searchDevices(): Flow<List<BluetoothDevice>> =
        bluetoothScannerDataSource.searchDevices()

    override fun scanState(): Flow<Boolean> =
        bluetoothScannerDataSource.scanState()


    override fun getSearchState(): Boolean =
        bluetoothScannerDataSource.getScanningState()
}