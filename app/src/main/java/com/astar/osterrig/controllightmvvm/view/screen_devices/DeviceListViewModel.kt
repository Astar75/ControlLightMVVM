package com.astar.osterrig.controllightmvvm.view.screen_devices

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.astar.osterrig.controllightmvvm.model.data.AppState
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.utils.Constants
import com.astar.osterrig.controllightmvvm.utils.convertingBluetoothDeviceToDeviceModel
import com.astar.osterrig.controllightmvvm.view.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.log

internal class DeviceListViewModel(private val interactor: DeviceListInteractorImplementation) :
    BaseViewModel() {

    private val mLiveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData()

    private val savedDeviceList: MutableList<DeviceModel> = ArrayList()
    private val foundDeviceList: MutableList<DeviceModel> = ArrayList()


    fun subscribe(): LiveData<AppState> {
        return mLiveDataForViewToObserve
    }

    fun getData() {
        cancelJob()
        viewModelCoroutineScope.launch {
            savedDeviceList.clear()
            savedDeviceList.addAll(interactor.getDevicesFromDatabase())
            mLiveDataForViewToObserve.postValue(AppState.Success(savedDeviceList))
        }
    }

    fun startScan() {
        cancelJob()
        if (!interactor.getSearchState()) {
            interactor.startScan()
        }
        viewModelCoroutineScope.launch {
            interactor.searchDevicesFlow().collect { foundDevices ->
                val dataDevices = convertingBluetoothDeviceToDeviceModel(foundDeviceList = foundDevices, savedDeviceList = savedDeviceList)
                foundDeviceList.clear()
                foundDeviceList.addAll(dataDevices)
                if (dataDevices.size > 2) {
                    saveFoundDeviceToDatabase()
                }
                mLiveDataForViewToObserve.postValue(AppState.Success(dataDevices))
            }
            interactor.scanState().collect {
                if (!it) {
                    saveFoundDeviceToDatabase()
                }
            }
        }
    }

    private suspend fun saveFoundDeviceToDatabase() {
        for (device in foundDeviceList) {
            interactor.addDeviceToDatabase(device)
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