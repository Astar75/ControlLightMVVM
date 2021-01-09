package com.astar.osterrig.controllightmvvm.view.screen_cct_control

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.astar.osterrig.controllightmvvm.model.data.CctColorEntity
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.view.viewmodels.ConnectionControlViewModel

internal class CctControlViewModel : ConnectionControlViewModel() {

    private val _lightness: MutableLiveData<Int> = MutableLiveData(0)
    val lightness: LiveData<Int>
        get() = _lightness

    private val _tint: MutableLiveData<Int> = MutableLiveData(50)
    val tint: LiveData<Int>
        get() = _tint

    val tintPreview: MutableLiveData<Int> = MutableLiveData(-50)

    fun setLightness(lightness: Int) {
        _lightness.value = lightness
    }

    fun setTint(tint: Int) {
        _tint.value = tint
        tintPreview.value = tint - 50
    }

    fun setColor(deviceModel: DeviceModel, item: CctColorEntity) {

    }

    override val connectionDeviceModel: DeviceModel
        get() = TODO("Not yet implemented")
}