package com.astar.osterrig.controllightmvvm.view.screen_devices

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astar.osterrig.controllightmvvm.model.data.AppState
import com.astar.osterrig.controllightmvvm.utils.convertingBluetoothDeviceToDeviceModel
import com.astar.osterrig.controllightmvvm.view.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

internal class DeviceListViewModel(private val interactor: DeviceListInteractor) : BaseViewModel() {

    private val liveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    fun startScan() {
        if (!interactor.getSearchState()) {
            interactor.startScan()
        }
        cancelJob()
        viewModelScope.launch {
            interactor.searchDeviceListLiveData().collect {
                Log.d(TAG, "Device count = ${it.size} ")
                for (device in it) {
                    Log.d(TAG, "Name: ${device.name}, Address ${device.address}")
                }
                liveDataForViewToObserve.postValue(AppState.Success(convertingBluetoothDeviceToDeviceModel(it)))
            }
        }
    }

    override fun handleError(error: Throwable) {
        liveDataForViewToObserve.value = AppState.Error(error)
        cancelJob()
    }

    companion object {
        const val TAG = "DeviceListViewModel"
    }
}