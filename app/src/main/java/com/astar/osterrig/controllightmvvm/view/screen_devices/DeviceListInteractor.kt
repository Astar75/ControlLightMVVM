package com.astar.osterrig.controllightmvvm.view.screen_devices

import android.bluetooth.BluetoothDevice
import com.astar.osterrig.controllightmvvm.model.repository.DeviceModelRepository
import kotlinx.coroutines.flow.Flow

class DeviceListInteractor(private val deviceModelRepository: DeviceModelRepository) {

    fun startScan() { deviceModelRepository.startScan() }

    fun searchDeviceListLiveData(): Flow<List<BluetoothDevice>> = deviceModelRepository.searchDevices()

    fun getSearchState(): Boolean = deviceModelRepository.getSearchState()
}