package com.astar.osterrig.controllightmvvm.view.viewmodels

import androidx.lifecycle.ViewModel
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel

internal abstract class ConnectionControlViewModel: ViewModel() {

    protected abstract val connectionDeviceModel: DeviceModel
}