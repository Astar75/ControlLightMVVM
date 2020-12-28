package com.astar.osterrig.controllightmvvm.view.screen_devices

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.astar.osterrig.controllightmvvm.model.data.AppState
import com.astar.osterrig.controllightmvvm.utils.convertingBluetoothDeviceToDeviceModel
import com.astar.osterrig.controllightmvvm.view.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

internal class DeviceListViewModel(private val interactor: DeviceListInteractorImplementation) : BaseViewModel() {

    private val mLiveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun subscribe(): LiveData<AppState> {
        return mLiveDataForViewToObserve
    }

    fun startScan() {
        cancelJob()
        if (!interactor.getSearchState()) {
            interactor.startScan()
        }
        viewModelCoroutineScope.launch {
            interactor.searchDevicesFlow().collect {
                Log.d(TAG, "Device count = ${it.size} ")
                for (device in it) {
                    Log.d(TAG, "Name: ${device.name}, Address ${device.address}")
                }
                mLiveDataForViewToObserve.postValue(AppState.Success(convertingBluetoothDeviceToDeviceModel(it)))
            }
        }
    }

    override fun handleError(error: Throwable) {
        mLiveDataForViewToObserve.value = AppState.Error(error)
        cancelJob()
    }

    companion object {
        const val TAG = "DeviceListViewModel"
    }
}