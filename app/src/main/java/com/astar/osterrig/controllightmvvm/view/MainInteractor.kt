package com.astar.osterrig.controllightmvvm.view

import com.astar.osterrig.controllightmvvm.model.data.AppState
import com.astar.osterrig.controllightmvvm.model.repository.DevicesRepository
import com.astar.osterrig.controllightmvvm.viewmodel.DeviceModelInteractor
import io.reactivex.Observable

class MainInteractor(
    private val localRepository: DevicesRepository
) : DeviceModelInteractor<AppState> {

    override fun getDevices(): Observable<AppState> {
        return localRepository.getDevices().map {
            AppState.Success(it)
        }
    }

    override fun getDeviceByMacAddress(macAddress: String): Observable<AppState> {
        return localRepository.getDeviceByMacAddress(macAddress).map {
            AppState.Success(it)
        }
    }
}