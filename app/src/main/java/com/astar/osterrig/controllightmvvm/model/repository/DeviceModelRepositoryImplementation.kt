package com.astar.osterrig.controllightmvvm.model.repository

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.LiveData
import com.astar.osterrig.controllightmvvm.model.data.AppState
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.model.datasource.bluetooth_scanner.BluetoothScannerDataSource
import com.astar.osterrig.controllightmvvm.model.datasource.persistence.DeviceModelDataSource
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

internal class DeviceModelRepositoryImplementation(
    private val dataSource: DeviceModelDataSource,
    private val bluetoothScannerDataSource: BluetoothScannerDataSource
) : DeviceModelRepository {

    override fun getDevices(): LiveData<List<DeviceModel>> {
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

    override suspend fun getDeviceFromGroup(groupName: String): List<DeviceModel> =
        dataSource.getDeviceFromGroup(groupName)

    override fun startScan() {
        bluetoothScannerDataSource.startScan()
    }

    override fun stopScan() {
        bluetoothScannerDataSource.stopScan()
    }

    override fun searchDevices(): Flow<List<BluetoothDevice>> =
        bluetoothScannerDataSource.searchDevices()

    override fun getSearchState(): Boolean =
        bluetoothScannerDataSource.getScanningState()
}