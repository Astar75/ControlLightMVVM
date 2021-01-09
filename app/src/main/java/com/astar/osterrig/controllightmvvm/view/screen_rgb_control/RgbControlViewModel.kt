package com.astar.osterrig.controllightmvvm.view.screen_rgb_control

import androidx.annotation.ColorInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.view.base.BaseViewModel
import java.lang.IllegalArgumentException

internal class RgbControlViewModel() : BaseViewModel() {

    private val _currentLightness: MutableLiveData<Int> = MutableLiveData()
    val currentLightness: LiveData<Int>
        get() = _currentLightness

    private val _currentColor: MutableLiveData<Int> = MutableLiveData()
    val currentColor: LiveData<Int>
        get() = _currentColor

    private val _colorPresetCellOne: MutableLiveData<Int> = MutableLiveData()
    val colorPresetCellOne: LiveData<Int>
        get() = _colorPresetCellOne
    private val _colorPresetCellTwo: MutableLiveData<Int> = MutableLiveData()
    val colorPresetCellTwo: LiveData<Int>
        get() = _colorPresetCellTwo
    private val _colorPresetCellThree: MutableLiveData<Int> = MutableLiveData()
    val colorPresetCellThree: LiveData<Int>
        get() = _colorPresetCellThree
    private val _colorPresetCellFour: MutableLiveData<Int> = MutableLiveData()
    val colorPresetCellFour: LiveData<Int>
        get() = _colorPresetCellFour

    fun setLightness(lightness: Int) {
        _currentLightness.value = lightness
    }

    fun setColor(color: Int) {
        _currentColor.value = color
    }

    fun setColorPresetCell(cell: Int, @ColorInt color: Int) {
        when (cell) {
            0 -> {
                _colorPresetCellOne.value = color
            }
            1 -> {
                _colorPresetCellTwo.value = color
            }
            2 -> {
                _colorPresetCellThree.value = color
            }
            3 -> {
                _colorPresetCellFour.value = color
            }
        }
    }

    override fun handleError(error: Throwable) {

    }
}

class RgbControlViewModelFactory(
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RgbControlViewModel::class.java)) {
            return RgbControlViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}