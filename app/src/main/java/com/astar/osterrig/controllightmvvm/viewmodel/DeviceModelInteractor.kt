package com.astar.osterrig.controllightmvvm.viewmodel

import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import io.reactivex.Observable

interface DeviceModelInteractor <T> {

    fun getDevices(): Observable<T>
    fun getDeviceByMacAddress(macAddress: String): Observable<T>
    fun addDevice(device: DeviceModel)
}