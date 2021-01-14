package com.astar.osterrig.controllightmvvm.view.screen_devices

import android.bluetooth.BluetoothDevice
import android.util.Log
import androidx.lifecycle.LiveData
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.model.repository.DeviceModelRepository
import kotlinx.coroutines.flow.Flow

class DeviceListInteractorImplementation(
    private val deviceModelRepository: DeviceModelRepository
) : DeviceListInteractor {

    override fun startScan() {
        deviceModelRepository.startScan()
    }

    override fun searchDevicesFlow(): Flow<List<BluetoothDevice>> =
        deviceModelRepository.searchDevices()

    override fun getSearchState(): Boolean = deviceModelRepository.getSearchState()

    override fun scanState(): Flow<Boolean> = deviceModelRepository.scanState()

    override suspend fun addDeviceToDatabase(deviceModel: DeviceModel) {
        deviceModelRepository.addDevice(deviceModel)
    }

    override suspend fun getDevicesFromDatabase(): List<DeviceModel> =
        deviceModelRepository.getDevices()

}