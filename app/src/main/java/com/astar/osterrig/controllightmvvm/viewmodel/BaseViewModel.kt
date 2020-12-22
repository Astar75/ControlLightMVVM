package com.astar.osterrig.controllightmvvm.viewmodel

import android.net.MacAddress
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astar.osterrig.controllightmvvm.model.data.AppState
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<T: AppState>(
    protected val liveDataForViewToObserve: MutableLiveData<T> = MutableLiveData(),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()
): ViewModel() {

    open fun getDevices(): LiveData<T> = liveDataForViewToObserve

    open fun getDeviceByMacAddress(macAddress: String): LiveData<T> = liveDataForViewToObserve

    override fun onCleared() {
        compositeDisposable.clear()
    }
}