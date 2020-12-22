package com.astar.osterrig.controllightmvvm.view

import androidx.lifecycle.LiveData
import com.astar.osterrig.controllightmvvm.model.data.AppState
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.model.datasource.DeviceModelDataSourceLocal
import com.astar.osterrig.controllightmvvm.model.repository.RepositoryImplementation
import com.astar.osterrig.controllightmvvm.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImplementation(DeviceModelDataSourceLocal())
    )
) : BaseViewModel<AppState>() {

    private var appState: AppState? = null

    override fun getDevices(): LiveData<AppState> {
        compositeDisposable.add(
            interactor.getDevices()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { liveDataForViewToObserve.value = AppState.Loading(null) }
                .subscribeWith(getObserver())
        )
        return super.getDevices()
    }

    override fun getDeviceByMacAddress(macAddress: String): LiveData<AppState> {
        compositeDisposable.add(
            interactor.getDeviceByMacAddress(macAddress)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { liveDataForViewToObserve.value = AppState.Loading(null) }
                .subscribeWith(getObserver())
        )
        return liveDataForViewToObserve
    }

    private fun getObserver(): DisposableObserver<AppState> {

        return object : DisposableObserver<AppState>() {

            override fun onNext(state: AppState) {
                appState = state
                liveDataForViewToObserve.value = state
            }

            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = AppState.Error(e)
            }

            override fun onComplete() {
            }
        }
    }

    fun addDevice(device: DeviceModel) {
        interactor.addDevice (device)
    }

}