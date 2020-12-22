package com.astar.osterrig.controllightmvvm.viewmodel

import io.reactivex.Observable

interface DeviceModelInteractor <T> {

    fun getDevices(): Observable<T>
    fun getDeviceByMacAddress(macAddress: String): Observable<T>
}