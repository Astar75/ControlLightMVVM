package com.astar.osterrig.controllightmvvm.model.repository

import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import io.reactivex.Observable

interface DevicesRepository {

    fun getDevices(): Observable<List<DeviceModel>>
    fun getDeviceByMacAddress(macAddress: String): Observable<DeviceModel?>
}