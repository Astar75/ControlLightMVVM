package com.astar.osterrig.controllightmvvm.model.datasource

import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import io.reactivex.Observable

interface DeviceModelDataSource {

    fun getDevices(): Observable<List<DeviceModel>>
    fun getDeviceByMacAddress(macAddress: String): Observable<DeviceModel?>
}