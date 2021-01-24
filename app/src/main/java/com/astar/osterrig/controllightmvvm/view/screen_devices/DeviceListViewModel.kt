package com.astar.osterrig.controllightmvvm.view.screen_devices

import androidx.lifecycle.MutableLiveData
import com.astar.osterrig.controllightmvvm.model.data.AppState
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.utils.convertingBLEToDeviceModel
import com.astar.osterrig.controllightmvvm.view.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

internal class DeviceListViewModel(
    private val interactor: DeviceListInteractorImplementation
) : BaseViewModel() {

    data class DeviceModelState(val macAddress: String, var connectionState: Boolean, var batteryLevel: Int)

    private val sabersFromDatabase: MutableList<DeviceModel> = ArrayList()

    private val liveDataToView: MutableLiveData<AppState> = MutableLiveData()

    private val devicesModelState: MutableList<DeviceModelState> = ArrayList()

    private val renderData: MutableList<DeviceModel> = ArrayList()

    fun subscribe() = liveDataToView

    private fun createDeviceModelState(macAddress: String) {
        if (devicesModelState.find { it.macAddress == macAddress } == null) {
            devicesModelState.add(DeviceModelState(macAddress, false, 0))
        }
    }

    fun updateBatteryLevel(macAddress: String, batteryLevel: Int) {
        createDeviceModelState(macAddress)
        devicesModelState.map {
            if (macAddress == it.macAddress) it.batteryLevel = batteryLevel
        }
        updateData()
    }

    fun updateConnectionState(macAddress: String, connectionState: Boolean) {
        createDeviceModelState(macAddress)
        devicesModelState.map {
            if (macAddress == it.macAddress) it.connectionState = connectionState
        }
        updateData()
    }

    private fun updateData() {
        renderData.map { deviceModel ->
            val state = devicesModelState.find { it.macAddress == deviceModel.macAddress }
            if (state != null) {
                deviceModel.isConnected = state.connectionState
                deviceModel.batteryLevel = state.batteryLevel
            }
        }
        liveDataToView.postValue(AppState.Success(renderData))
    }

    fun loadSabersFromDeviceModel() {
        viewModelCoroutineScope.launch {
            val sabers = interactor.getDevicesFromDatabase()

            sabersFromDatabase.clear()
            sabersFromDatabase.addAll(sabers)
            /*Log.d(TAG, "loadSabersFromDeviceModel: Start printInfo")
            for (saber in sabersFromDatabase) {
                Log.d(TAG, "    saber: ${saber.macAddress}, ${saber.isConnected},  ${saber.batteryLevel}")
            }*/
            renderData.clear()
            renderData.addAll(sabersFromDatabase)

            updateData()
        }
    }

    fun searchSabers() {
        cancelJob()
        if (!interactor.getSearchState()) {
            interactor.startScan()
        }
        viewModelCoroutineScope.launch {
            val tmpForSave: MutableList<DeviceModel> = arrayListOf()
            interactor.searchDevicesFlow().collect { foundDevices ->
                val dataDevices = convertingBLEToDeviceModel(
                    foundDevices,
                    sabersFromDatabase
                )
                tmpForSave.clear()
                tmpForSave.addAll(dataDevices)

                renderData.clear()
                renderData.addAll(dataDevices)
                updateData()
            }
            interactor.scanState().collect { isScanning ->
                if (!isScanning) {
                    saveFoundSabersToDatabase(tmpForSave)
                }
            }
        }
    }

    private suspend fun saveFoundSabersToDatabase(sabers: List<DeviceModel>) {
        for (saber in sabers) {
            interactor.addDeviceToDatabase(saber)
        }
    }

    override fun handleError(error: Throwable) {
        liveDataToView.value = AppState.Error(error)
        cancelJob()
    }

    companion object {
        const val TAG = "DeviceListViewModel"
    }
}

