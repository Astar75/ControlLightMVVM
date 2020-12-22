package com.astar.osterrig.controllightmvvm.view

import androidx.appcompat.app.AppCompatActivity
import com.astar.osterrig.controllightmvvm.model.data.AppState
import com.astar.osterrig.controllightmvvm.viewmodel.BaseViewModel
import com.astar.osterrig.controllightmvvm.viewmodel.DeviceModelInteractor

abstract class BaseActivity<T: AppState, I: DeviceModelInteractor<T>>: AppCompatActivity() {

    abstract val model: BaseViewModel<T>

    abstract fun renderData(dataModel: T)
}