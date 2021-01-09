package com.astar.osterrig.controllightmvvm.view.screen_ftp_control

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.view.base.BaseViewModel
import com.astar.osterrig.controllightmvvm.view.viewmodels.ConnectionControlViewModel

internal class FtpControlViewModel : ConnectionControlViewModel() {

    val currentColor: MutableLiveData<Int> = MutableLiveData(0)

    private val _redColor: MutableLiveData<Int> = MutableLiveData(0)
    val redColor: LiveData<Int>
        get() = _redColor

    private val _greenColor: MutableLiveData<Int> = MutableLiveData(0)
    val greenColor: LiveData<Int>
        get() = _greenColor

    private val _blueColor: MutableLiveData<Int> = MutableLiveData(0)
    val blueColor: LiveData<Int>
        get() = _blueColor

    private val _saturationColor: MutableLiveData<Int> = MutableLiveData(0)
    val saturationColor: LiveData<Int>
        get() = _saturationColor

    private val _lightness: MutableLiveData<Int> = MutableLiveData(0)
    val lightness: LiveData<Int>
        get() = _lightness

    private val _colorOne: MutableLiveData<Int> = MutableLiveData()
    val colorOne: LiveData<Int>
        get() = _colorOne

    private val _colorTwo: MutableLiveData<Int> = MutableLiveData()
    val colorTwo: LiveData<Int>
        get() = _colorTwo

    private val _colorThree: MutableLiveData<Int> = MutableLiveData()
    val colorThree: LiveData<Int>
        get() = _colorThree

    private val _colorFour: MutableLiveData<Int> = MutableLiveData()
    val colorFour: LiveData<Int>
        get() = _colorFour

    private var currentColorCell = 0

    private var red: Int = 0
    private var green: Int = 0
    private var blue: Int = 0

    fun setRed(redColor: Int) {
        _redColor.value = redColor
        red = redColor
        setCurrentColor()
        setColorCell()
    }

    fun setGreen(greenColor: Int) {
        _greenColor.value = greenColor
        green = greenColor
        setCurrentColor()
        setColorCell()
    }

    fun setBlue(blueColor: Int) {
        _blueColor.value = blueColor
        blue = blueColor
        setCurrentColor()
        setColorCell()
    }

    fun setSaturation(saturation: Int) {
        _saturationColor.value = saturation
    }

    fun setLightness(lightness: Int) {
        _lightness.value = lightness
    }

    fun setCurrentColorCell(cell: Int) {
        currentColorCell = cell
    }

    private fun setColorCell() {
        when(currentColorCell) {
            0 -> { _colorOne.value = currentColor.value }
            1 -> { _colorTwo.value = currentColor.value }
            2 -> { _colorThree.value = currentColor.value }
            3 -> { _colorFour.value = currentColor.value }
        }
    }

    private fun setCurrentColor() {
        currentColor.value = Color.rgb(red, green, blue)
    }

    override val connectionDeviceModel: DeviceModel
        get() = TODO("Not yet implemented")
}