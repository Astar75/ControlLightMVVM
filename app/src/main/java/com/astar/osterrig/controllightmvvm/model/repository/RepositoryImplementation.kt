package com.astar.osterrig.controllightmvvm.model.repository

import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.model.datasource.DeviceModelDataSource
import io.reactivex.Observable

class RepositoryImplementation(private val dataSource: DeviceModelDataSource) : DevicesRepository {

    override fun getDevices(): Observable<List<DeviceModel>> {
        return dataSource.getDevices()
    }

    override fun getDeviceByMacAddress(macAddress: String): Observable<DeviceModel?> {
        return dataSource.getDeviceByMacAddress(macAddress)
    }
}