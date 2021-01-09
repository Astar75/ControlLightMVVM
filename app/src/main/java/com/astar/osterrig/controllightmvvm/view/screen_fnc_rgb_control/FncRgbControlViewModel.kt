package com.astar.osterrig.controllightmvvm.view.screen_fnc_rgb_control

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astar.osterrig.controllightmvvm.model.data.FunctionRgb
import com.astar.osterrig.controllightmvvm.utils.DataProvider


class FncRgbControlViewModel : ViewModel() {

    private val _cellFunctionOne: MutableLiveData<FunctionRgb> = MutableLiveData()
    val cellFunctionOne: LiveData<FunctionRgb>
        get() = _cellFunctionOne

    private val _cellFunctionTwo: MutableLiveData<FunctionRgb> = MutableLiveData()
    val cellFunctionTwo: LiveData<FunctionRgb>
        get() = _cellFunctionTwo

    private val _cellFunctionThree: MutableLiveData<FunctionRgb> = MutableLiveData()
    val cellFunctionThree: LiveData<FunctionRgb>
        get() = _cellFunctionThree

    private val _cellFunctionFour: MutableLiveData<FunctionRgb> = MutableLiveData()
    val cellFunctionFour: LiveData<FunctionRgb>
        get() = _cellFunctionFour

    private val _cellFunctionFive: MutableLiveData<FunctionRgb> = MutableLiveData()
    val cellFunctionFive: LiveData<FunctionRgb>
        get() = _cellFunctionFive

    private val _cellFunctionSix: MutableLiveData<FunctionRgb> = MutableLiveData()
    val cellFunctionSix: LiveData<FunctionRgb>
        get() = _cellFunctionSix

    private val _cellFunctionSeven: MutableLiveData<FunctionRgb> = MutableLiveData()
    val cellFunctionSeven: LiveData<FunctionRgb>
        get() = _cellFunctionSeven

    private val _cellFunctionEight: MutableLiveData<FunctionRgb> = MutableLiveData()
    val cellFunctionEight: LiveData<FunctionRgb>
        get() = _cellFunctionEight

    private val _cellFunctionNine: MutableLiveData<FunctionRgb> = MutableLiveData()
    val cellFunctionNine: LiveData<FunctionRgb>
        get() = _cellFunctionNine

    private val _selectedFunction: MutableLiveData<FunctionRgb> = MutableLiveData()
    val selectedFunction: LiveData<FunctionRgb>
        get() = _selectedFunction

    private val _lightnessPreview: MutableLiveData<Int> = MutableLiveData(0)
    val lightnessPreview: LiveData<Int>
        get() = _lightnessPreview

    private val _speedPreview: MutableLiveData<Int> = MutableLiveData(0)
    val speedPreview: LiveData<Int>
        get() = _speedPreview

    private val _sendFunction: MutableLiveData<Int> = MutableLiveData()
    val sendFunction: LiveData<Int>
        get() = _sendFunction

    init {
        val functionsData = DataProvider.getRgbFunctions()

        _cellFunctionOne.value = functionsData[0]
        _cellFunctionTwo.value = functionsData[1]
        _cellFunctionThree.value = functionsData[2]
        _cellFunctionFour.value = functionsData[3]
        _cellFunctionFive.value = functionsData[4]
        _cellFunctionSix.value = functionsData[5]
        _cellFunctionSeven.value = functionsData[6]
        _cellFunctionEight.value = functionsData[7]
        _cellFunctionNine.value = functionsData[8]
    }

    fun selectFunction(cell: Int) {
        when (cell) {
            0 -> {
                _selectedFunction.value = _cellFunctionOne.value
            }
            1 -> {
                _selectedFunction.value = _cellFunctionTwo.value
            }
            2 -> {
                _selectedFunction.value = _cellFunctionThree.value
            }
            3 -> {
                _selectedFunction.value = _cellFunctionFour.value
            }
            4 -> {
                _selectedFunction.value = _cellFunctionFive.value
            }
            5 -> {
                _selectedFunction.value = _cellFunctionSix.value
            }
            6 -> {
                _selectedFunction.value = _cellFunctionSeven.value
            }
            7 -> {
                _selectedFunction.value = _cellFunctionEight.value
            }
            8 -> {
                _selectedFunction.value = _cellFunctionNine.value
            }
        }

        _sendFunction.value = _selectedFunction.value?.code
    }

    fun setLightnessCell(cell: Int, lightness: Int) {
        when (cell) {
            0 -> {
                _cellFunctionOne.value =
                    _cellFunctionOne.value?.let { applyChangesLightness(it, lightness) }
            }
            1 -> {
                _cellFunctionTwo.value =
                    _cellFunctionTwo.value?.let { applyChangesLightness(it, lightness) }
            }
            2 -> {
                _cellFunctionThree.value =
                    _cellFunctionThree.value?.let { applyChangesLightness(it, lightness) }
            }
            3 -> {
                _cellFunctionFour.value =
                    _cellFunctionFour.value?.let { applyChangesLightness(it, lightness) }
            }
            4 -> {
                _cellFunctionFive.value =
                    _cellFunctionFive.value?.let { applyChangesLightness(it, lightness) }
            }
            5 -> {
                _cellFunctionSix.value =
                    _cellFunctionSix.value?.let { applyChangesLightness(it, lightness) }
            }
            6 -> {
                _cellFunctionSeven.value =
                    _cellFunctionSeven.value?.let { applyChangesLightness(it, lightness) }
            }
            7 -> {
                _cellFunctionEight.value =
                    _cellFunctionEight.value?.let { applyChangesLightness(it, lightness) }
            }
            8 -> {
                _cellFunctionNine.value =
                    _cellFunctionNine.value?.let { applyChangesLightness(it, lightness) }
            }
        }
    }

    private fun applyChangesLightness(
        currentFunction: FunctionRgb,
        lightness: Int
    ): FunctionRgb {
        currentFunction.lightness = lightness
        return currentFunction
    }

    fun setSpeedCell(cell: Int, speed: Int) {
        when (cell) {
            0 -> {
                _cellFunctionOne.value =
                    _cellFunctionOne.value?.let { applyChangesSpeed(it, speed) }
            }
            1 -> {
                _cellFunctionTwo.value =
                    _cellFunctionTwo.value?.let { applyChangesSpeed(it, speed) }
            }
            2 -> {
                _cellFunctionThree.value =
                    _cellFunctionThree.value?.let { applyChangesSpeed(it, speed) }
            }
            3 -> {
                _cellFunctionFour.value =
                    _cellFunctionFour.value?.let { applyChangesSpeed(it, speed) }
            }
            4 -> {
                _cellFunctionFive.value =
                    _cellFunctionFive.value?.let { applyChangesSpeed(it, speed) }
            }
            5 -> {
                _cellFunctionSix.value =
                    _cellFunctionSix.value?.let { applyChangesSpeed(it, speed) }
            }
            6 -> {
                _cellFunctionSeven.value =
                    _cellFunctionSeven.value?.let { applyChangesSpeed(it, speed) }
            }
            7 -> {
                _cellFunctionEight.value =
                    _cellFunctionEight.value?.let { applyChangesSpeed(it, speed) }
            }
            8 -> {
                _cellFunctionNine.value =
                    _cellFunctionNine.value?.let { applyChangesSpeed(it, speed) }
            }
        }
    }

    private fun applyChangesSpeed(
        currentFunction: FunctionRgb,
        speed: Int
    ): FunctionRgb {
        currentFunction.speed = speed
        return currentFunction
    }

    fun setLightness(progress: Int) {

    }

    fun setSpeed(progress: Int) {

    }
}