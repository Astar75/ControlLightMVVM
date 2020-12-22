package com.astar.osterrig.controllightmvvm.model.datasource

import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import io.reactivex.Observable

class RoomDatabaseImplementation {

    private val devices = mutableListOf(
        DeviceModel("00:00:AA:10:01", "Osterrig WALS+ (001)", ""),
        DeviceModel("00:45:3A:15:04", "Osterrig RGB (110)", ""),
        DeviceModel("0B:12:AA:90:89", "Osterrig WALS+ (003)", "Solaris"),
        DeviceModel("90:BD:B7:67:31", "Osterrig TC (694)", "Office"),
        DeviceModel("00:00:4A:17:AC", "Osterrig TC (474)", "Office"),
        DeviceModel("56:00:BC:18:31", "Osterrig RGB (010)", "")
    )

    fun getDevices(): Observable<List<DeviceModel>> {
        return Observable.just(devices)
    }

    fun getDeviceByMacAddress(macAddress: String): Observable<DeviceModel?> {
        val device = devices.firstOrNull {
            it.macAddress == macAddress
        }
        return if (device != null)
            Observable.just(device)
        else
            Observable.just(DeviceModel("", "", ""))
    }

    fun addDevice(device: DeviceModel) {
        if (!devices.contains(device))
            devices.add(device)
    }

}