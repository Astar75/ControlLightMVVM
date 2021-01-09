package com.astar.osterrig.controllightmvvm.view.screen_fnc_control

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.model.data.FunctionWals
import com.astar.osterrig.controllightmvvm.utils.DataProvider
import com.astar.osterrig.controllightmvvm.view.viewmodels.ConnectionControlViewModel

internal class FncControlViewModel : ConnectionControlViewModel() {

    private val _cellFunctionOne: MutableLiveData<FunctionWals> = MutableLiveData()
    val cellFunctionOne: LiveData<FunctionWals>
        get() = _cellFunctionOne

    private val _cellFunctionTwo: MutableLiveData<FunctionWals> = MutableLiveData()
    val cellFunctionTwo: LiveData<FunctionWals>
        get() = _cellFunctionTwo

    private val _cellFunctionThree: MutableLiveData<FunctionWals> = MutableLiveData()
    val cellFunctionThree: LiveData<FunctionWals>
        get() = _cellFunctionThree

    private val _cellFunctionFour: MutableLiveData<FunctionWals> = MutableLiveData()
    val cellFunctionFour: LiveData<FunctionWals>
        get() = _cellFunctionFour

    private val _cellFunctionFive: MutableLiveData<FunctionWals> = MutableLiveData()
    val cellFunctionFive: LiveData<FunctionWals>
        get() = _cellFunctionFive

    private val _cellFunctionSix: MutableLiveData<FunctionWals> = MutableLiveData()
    val cellFunctionSix: LiveData<FunctionWals>
        get() = _cellFunctionSix

    private val _cellFunctionSeven: MutableLiveData<FunctionWals> = MutableLiveData()
    val cellFunctionSeven: LiveData<FunctionWals>
        get() = _cellFunctionSeven

    private val _cellFunctionEight: MutableLiveData<FunctionWals> = MutableLiveData()
    val cellFunctionEight: LiveData<FunctionWals>
        get() = _cellFunctionEight

    private val _cellFunctionNine: MutableLiveData<FunctionWals> = MutableLiveData()
    val cellFunctionNine: LiveData<FunctionWals>
        get() = _cellFunctionNine

    private val _selectedFunction: MutableLiveData<FunctionWals> = MutableLiveData()
    val selectedFunction: LiveData<FunctionWals>
        get() = _selectedFunction

    private val _rotationFunction: MutableLiveData<Boolean> = MutableLiveData(true)
    val rotationFunction: LiveData<Boolean>
        get() = _rotationFunction

    private val _lightnessPreview: MutableLiveData<Int> = MutableLiveData(0)
    val lightnessPreview: LiveData<Int>
        get() = _lightnessPreview

    private val _speedPreview: MutableLiveData<Int> = MutableLiveData(0)
    val speedPreview: LiveData<Int>
        get() = _speedPreview

    init {
        val dataFunction = DataProvider.getWalsFunctions()
        for (i in dataFunction.indices) {
            for (j in dataFunction[i].colorArray.indices)
                dataFunction[i].colorArray[j] = Color.BLACK
        }
        _cellFunctionOne.value = dataFunction[0]
        _cellFunctionTwo.value = dataFunction[1]
        _cellFunctionThree.value = dataFunction[2]
        _cellFunctionFour.value = dataFunction[3]
        _cellFunctionFive.value = dataFunction[4]
        _cellFunctionSix.value = dataFunction[5]
        _cellFunctionSeven.value = dataFunction[6]
        _cellFunctionEight.value = dataFunction[7]
        _cellFunctionNine.value = dataFunction[8]

        _selectedFunction.value = _cellFunctionOne.value
    }

    fun rotateFunction() {
        _rotationFunction.value?.let { _rotationFunction.value = !it }
    }

    fun getStringAndColorArray(source: Array<String>): List<Pair<Int, String>> {
        val list = mutableListOf<Pair<Int, String>>()
        val colors = _selectedFunction.value!!.colorArray
        for (i in source.indices) {
            list.add(Pair(colors[i], source[i]))
        }
        return list
    }

    fun selectFunction(cell: Int) {
        when (cell) {
            0 -> { _selectedFunction.value = _cellFunctionOne.value }
            1 -> { _selectedFunction.value = _cellFunctionTwo.value }
            2 -> { _selectedFunction.value = _cellFunctionThree.value }
            3 -> { _selectedFunction.value = _cellFunctionFour.value }
            4 -> { _selectedFunction.value = _cellFunctionFive.value }
            5 -> { _selectedFunction.value = _cellFunctionSix.value }
            6 -> { _selectedFunction.value = _cellFunctionSeven.value }
            7 -> { _selectedFunction.value = _cellFunctionEight.value }
            8 -> { _selectedFunction.value = _cellFunctionNine.value }
        }
        sendCommand()
    }

    // TODO: 08.01.2021 Отправка команды на лампу для WALS
    private fun sendCommand() {
        val functionWals: FunctionWals? = selectedFunction.value
        functionWals?.let { function ->
            val buildCommand = "c:${function.code};"
        }
    }

    fun setSmooth(cell: Int, isSmooth: Boolean) {
        when (cell) {
            0 -> { _cellFunctionOne.value = _cellFunctionOne.value?.let { applyChangesSmooth(it, isSmooth) } }
            1 -> { _cellFunctionTwo.value = _cellFunctionTwo.value?.let { applyChangesSmooth(it, isSmooth) } }
            2 -> { _cellFunctionThree.value = _cellFunctionThree.value?.let { applyChangesSmooth(it, isSmooth) } }
            3 -> { _cellFunctionFour.value = _cellFunctionFour.value?.let { applyChangesSmooth(it, isSmooth) } }
            4 -> { _cellFunctionFive.value = _cellFunctionFive.value?.let { applyChangesSmooth(it, isSmooth) } }
            5 -> { _cellFunctionSix.value = _cellFunctionSix.value?.let { applyChangesSmooth(it, isSmooth) } }
            6 -> { _cellFunctionSeven.value = _cellFunctionSeven.value?.let { applyChangesSmooth(it, isSmooth) } }
            7 -> { _cellFunctionEight.value = _cellFunctionEight.value?.let { applyChangesSmooth(it, isSmooth) } }
            8 -> { _cellFunctionNine.value = _cellFunctionNine.value?.let { applyChangesSmooth(it, isSmooth) } }
        }
    }

    private fun applyChangesSmooth(
        currentFunction: FunctionWals,
        isSmooth: Boolean
    ): FunctionWals {
        currentFunction.isSmooth = isSmooth
        return currentFunction
    }

    fun setColorToItemFunction(
        cell: Int,
        selectedColorItem: Int,
        color: Int
    ) {
        when (cell) {
            0 -> { _cellFunctionOne.value = _cellFunctionOne.value?.let { applyChangesColor(it, selectedColorItem, color) } }
            1 -> { _cellFunctionTwo.value = _cellFunctionTwo.value?.let { applyChangesColor(it, selectedColorItem, color) } }
            2 -> { _cellFunctionThree.value = _cellFunctionThree.value?.let { applyChangesColor(it, selectedColorItem,                 color) } }
            3 -> { _cellFunctionFour.value = _cellFunctionFour.value?.let { applyChangesColor(it, selectedColorItem, color) } }
            4 -> { _cellFunctionFive.value = _cellFunctionFive.value?.let { applyChangesColor(it, selectedColorItem, color) } }
            5 -> { _cellFunctionSix.value = _cellFunctionSix.value?.let { applyChangesColor(it, selectedColorItem, color) } }
            6 -> { _cellFunctionSeven.value = _cellFunctionSeven.value?.let { applyChangesColor(it, selectedColorItem, color) } }
            7 -> { _cellFunctionEight.value = _cellFunctionEight.value?.let { applyChangesColor(it, selectedColorItem, color) } }
            8 -> { _cellFunctionNine.value = _cellFunctionNine.value?.let { applyChangesColor(it, selectedColorItem, color) } }
        }
    }

    private fun applyChangesColor(
        currentFunction: FunctionWals,
        selectedColorItem: Int,
        color: Int
    ): FunctionWals {
        currentFunction.colorArray[selectedColorItem] = color
        return currentFunction
    }

    fun updateSelectedCell() {
        _selectedFunction.value = _selectedFunction.value
    }

    fun setFunctionToCell(cell: Int, code: Int, name: String, icon: Int) {
        when (cell) {
            0 -> { _cellFunctionOne.value = _cellFunctionOne.value?.let { applyChangeFunction(it, code, name, icon) } }
            1 -> { _cellFunctionTwo.value = _cellFunctionTwo.value?.let { applyChangeFunction(it, code, name, icon) } }
            2 -> { _cellFunctionThree.value = _cellFunctionThree.value?.let { applyChangeFunction(it, code, name, icon) } }
            3 -> { _cellFunctionFour.value = _cellFunctionFour.value?.let { applyChangeFunction(it, code, name, icon) } }
            4 -> { _cellFunctionFive.value = _cellFunctionFive.value?.let { applyChangeFunction(it, code, name, icon) } }
            5 -> { _cellFunctionSix.value = _cellFunctionSix.value?.let { applyChangeFunction(it, code, name, icon) } }
            6 -> { _cellFunctionSeven.value = _cellFunctionSeven.value?.let { applyChangeFunction(it, code, name, icon) } }
            7 -> { _cellFunctionEight.value = _cellFunctionEight.value?.let { applyChangeFunction(it, code, name, icon) } }
            8 -> { _cellFunctionNine.value = _cellFunctionNine.value?.let { applyChangeFunction(it, code, name, icon) } }
        }
    }

    private fun applyChangeFunction(
        currentFunction: FunctionWals,
        code: Int, name: String, icon: Int
    ): FunctionWals {
        currentFunction.code = code
        currentFunction.name = name
        currentFunction.icon = icon
        return currentFunction
    }

    fun setLightness(lightness: Int) {
        // TODO: 08.01.2021 запилить яркость
        _lightnessPreview.value = lightness
    }

    fun setSpeed(speed: Int) {
        // TODO: 08.01.2021 запилить скорость
        _speedPreview.value = speed
    }

    fun setLightnessFunction(cell: Int, lightness: Int) {
        when (cell) {
            0 -> { _cellFunctionOne.value = _cellFunctionOne.value?.let { applyLightnessFunction(it, lightness) } }
            1 -> { _cellFunctionTwo.value = _cellFunctionTwo.value?.let { applyLightnessFunction(it, lightness) } }
            2 -> { _cellFunctionThree.value = _cellFunctionThree.value?.let { applyLightnessFunction(it, lightness) } }
            3 -> { _cellFunctionFour.value = _cellFunctionFour.value?.let { applyLightnessFunction(it, lightness) } }
            4 -> { _cellFunctionFive.value = _cellFunctionFive.value?.let { applyLightnessFunction(it, lightness) } }
            5 -> { _cellFunctionSix.value = _cellFunctionSix.value?.let { applyLightnessFunction(it, lightness) } }
            6 -> { _cellFunctionSeven.value = _cellFunctionSeven.value?.let { applyLightnessFunction(it, lightness) } }
            7 -> { _cellFunctionEight.value = _cellFunctionEight.value?.let { applyLightnessFunction(it, lightness) } }
            8 -> { _cellFunctionNine.value = _cellFunctionNine.value?.let { applyLightnessFunction(it, lightness) } }
        }
    }

    private fun applyLightnessFunction(
        currentFunction: FunctionWals,
        lightness: Int
    ): FunctionWals {
        currentFunction.lightness = lightness
        return currentFunction
    }

    fun setSpeedFunction(cell: Int, speed: Int) {
        when (cell) {
            0 -> { _cellFunctionOne.value = _cellFunctionOne.value?.let { applySpeedFunction(it, speed) } }
            1 -> { _cellFunctionTwo.value = _cellFunctionTwo.value?.let { applySpeedFunction(it, speed) } }
            2 -> { _cellFunctionThree.value = _cellFunctionThree.value?.let { applySpeedFunction(it, speed) } }
            3 -> { _cellFunctionFour.value = _cellFunctionFour.value?.let { applySpeedFunction(it, speed) } }
            4 -> { _cellFunctionFive.value = _cellFunctionFive.value?.let { applySpeedFunction(it, speed) } }
            5 -> { _cellFunctionSix.value = _cellFunctionSix.value?.let { applySpeedFunction(it, speed) } }
            6 -> { _cellFunctionSeven.value = _cellFunctionSeven.value?.let { applySpeedFunction(it, speed) } }
            7 -> { _cellFunctionEight.value = _cellFunctionEight.value?.let { applySpeedFunction(it, speed) } }
            8 -> { _cellFunctionNine.value = _cellFunctionNine.value?.let { applySpeedFunction(it, speed) } }
        }
    }

    private fun applySpeedFunction(
        currentFunction: FunctionWals,
        speed: Int
    ): FunctionWals {
        currentFunction.speed = speed
        return currentFunction
    }

    override val connectionDeviceModel: DeviceModel
        get() = TODO("Not yet implemented")
}