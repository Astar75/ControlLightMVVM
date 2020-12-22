package com.astar.osterrig.controllightmvvm.model.datasource

import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import io.reactivex.Observable

class DeviceModelDataSourceLocal(
    private val provider: RoomDatabaseImplementation = RoomDatabaseImplementation()
) : DeviceModelDataSource {

    override fun getDevices(): Observable<List<DeviceModel>> {
        return provider.getDevices()
    }

    override fun getDeviceByMacAddress(macAddress: String): Observable<DeviceModel?> {
        return provider.getDeviceByMacAddress(macAddress)
    }

    override fun addDevice(device: DeviceModel) {
        provider.addDevice(device)
    }
}