package com.astar.osterrig.controllightmvvm.view.screen_devices

import android.bluetooth.BluetoothDevice
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
}