
package com.astar.osterrig.controllightmvvm.view.screen_fnc_control

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.lifecycle.*
import com.astar.osterrig.controllightmvvm.model.data.FunctionWals
import com.astar.osterrig.controllightmvvm.utils.Constants
import com.astar.osterrig.controllightmvvm.utils.DataProvider

internal class FncControlViewModel : ViewModel() {

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

    private val _lightnessPreview: MutableLiveData<Int> = MutableLiveData(0)
    val lightnessPreviewIndicator: LiveData<Int>
        get() = _lightnessPreview

    private val _speedPreview: MutableLiveData<Int> = MutableLiveData(0)
    val speedPreviewIndicator: LiveData<Int>
        get() = _speedPreview

    private val _blockSpeedSeekBar: MutableLiveData<Boolean> = MutableLiveData(true)
    val blockSpeedSeekBar: LiveData<Boolean>
        get() = _blockSpeedSeekBar

    private val _blockLightnessSeekBar: MutableLiveData<Boolean> = MutableLiveData(true)
    val blockLightnessSeekBar: LiveData<Boolean>
        get() = _blockLightnessSeekBar

    private val _blockSpinnerOne: MutableLiveData<Boolean> = MutableLiveData(true)
    val blockSpinnerOne: LiveData<Boolean>
        get() = _blockSpinnerOne

    private val _blockSpinnerTwo: MutableLiveData<Boolean> = MutableLiveData(false)
    val blockSpinnerTwo: LiveData<Boolean>
        get() = _blockSpinnerTwo

    private val _blockSpinnerThree: MutableLiveData<Boolean> = MutableLiveData(true)
    val blockSpinnerThree: LiveData<Boolean>
        get() = _blockSpinnerThree

    private val _blockPreviewFunction: MutableLiveData<Boolean> = MutableLiveData(true)
    val blockPreviewFunction: LiveData<Boolean>
        get() = _blockPreviewFunction

    init {
        val dataFunction = DataProvider.getPresetWalsFunctions()
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

    fun getStringAndColorArray(source: Array<String>): List<Pair<Int, String>> {
        val list = mutableListOf<Pair<Int, String>>()
        val colors = _selectedFunction.value!!.colorArray
        for (i in source.indices) {
            list.add(Pair(colors[i], source[i]))
        }
        return list
    }

    fun selectFunctionCell(cell: Int) {
        when(cell) {
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
        updateSelectedCell()
    }

    fun changeDirectionFunction(cell: Int) {
        when(cell) {
            0 -> { _cellFunctionOne.value = _cellFunctionOne.value?.let { it.apply { isReverse = !isReverse } } }
            1 -> { _cellFunctionTwo.value = _cellFunctionTwo.value?.let { it.apply { isReverse = !isReverse } } }
            2 -> { _cellFunctionThree.value = _cellFunctionThree.value?.let { it.apply { isReverse = !isReverse } } }
            3 -> { _cellFunctionFour.value = _cellFunctionFour.value?.let { it.apply { isReverse = !isReverse } } }
            4 -> { _cellFunctionFive.value = _cellFunctionFive.value?.let { it.apply { isReverse = !isReverse } } }
            5 -> { _cellFunctionSix.value = _cellFunctionSix.value?.let { it.apply { isReverse = !isReverse } } }
            6 -> { _cellFunctionSeven.value = _cellFunctionSeven.value?.let { it.apply { isReverse = !isReverse } } }
            7 -> { _cellFunctionEight.value = _cellFunctionEight.value?.let { it.apply { isReverse = !isReverse } } }
            8 -> { _cellFunctionNine.value = _cellFunctionNine.value?.let { it.apply { isReverse = !isReverse } } }
        }
        updateSelectedCell()
    }

    fun changeSmoothFunction(cell: Int, smooth: Boolean) {
        when(cell) {
            0 -> { _cellFunctionOne.value = _cellFunctionOne.value?.let { it.apply { it.isSmooth = smooth } } }
            1 -> { _cellFunctionTwo.value = _cellFunctionTwo.value?.let { it.apply { it.isSmooth = smooth } } }
            2 -> { _cellFunctionThree.value = _cellFunctionThree.value?.let { it.apply { it.isSmooth = smooth } } }
            3 -> { _cellFunctionFour.value = _cellFunctionFour.value?.let { it.apply { it.isSmooth = smooth } } }
            4 -> { _cellFunctionFive.value = _cellFunctionFive.value?.let { it.apply { it.isSmooth = smooth } } }
            5 -> { _cellFunctionSix.value = _cellFunctionSix.value?.let { it.apply { it.isSmooth = smooth } } }
            6 -> { _cellFunctionSeven.value = _cellFunctionSeven.value?.let { it.apply { it.isSmooth = smooth } } }
            7 -> { _cellFunctionEight.value = _cellFunctionEight.value?.let { it.apply { it.isSmooth = smooth } } }
            8 -> { _cellFunctionNine.value = _cellFunctionNine.value?.let { it.apply { it.isSmooth = smooth } } }
        }
        updateSelectedCell()
    }

    fun changeLightnessFunction(cell: Int, lightnessValue: Int) {
        when(cell) {
            0 -> { _cellFunctionOne.value = _cellFunctionOne.value?.let { it.apply { it.lightness = lightnessValue } } }
            1 -> { _cellFunctionTwo.value = _cellFunctionTwo.value?.let { it.apply { it.lightness = lightnessValue } } }
            2 -> { _cellFunctionThree.value = _cellFunctionThree.value?.let { it.apply { it.lightness = lightnessValue } } }
            3 -> { _cellFunctionFour.value = _cellFunctionFour.value?.let { it.apply { it.lightness = lightnessValue } } }
            4 -> { _cellFunctionFive.value = _cellFunctionFive.value?.let { it.apply { it.lightness = lightnessValue } } }
            5 -> { _cellFunctionSix.value = _cellFunctionSix.value?.let { it.apply { it.lightness = lightnessValue } } }
            6 -> { _cellFunctionSeven.value = _cellFunctionSeven.value?.let { it.apply { it.lightness = lightnessValue } } }
            7 -> { _cellFunctionEight.value = _cellFunctionEight.value?.let { it.apply { it.lightness = lightnessValue } } }
            8 -> { _cellFunctionNine.value = _cellFunctionNine.value?.let { it.apply { it.lightness = lightnessValue } } }
        }
        updateSelectedCell()
    }

    fun changeSpeedFunction(cell: Int, speedValue: Int) {
        when(cell) {
            0 -> { _cellFunctionOne.value = _cellFunctionOne.value?.let { it.apply { it.speed = speedValue } } }
            1 -> { _cellFunctionTwo.value = _cellFunctionTwo.value?.let { it.apply { it.speed = speedValue } } }
            2 -> { _cellFunctionThree.value = _cellFunctionThree.value?.let { it.apply { it.speed = speedValue } } }
            3 -> { _cellFunctionFour.value = _cellFunctionFour.value?.let { it.apply { it.speed = speedValue } } }
            4 -> { _cellFunctionFive.value = _cellFunctionFive.value?.let { it.apply { it.speed = speedValue } } }
            5 -> { _cellFunctionSix.value = _cellFunctionSix.value?.let { it.apply { it.speed = speedValue } } }
            6 -> { _cellFunctionSeven.value = _cellFunctionSeven.value?.let { it.apply { it.speed = speedValue } } }
            7 -> { _cellFunctionEight.value = _cellFunctionEight.value?.let { it.apply { it.speed = speedValue } } }
            8 -> { _cellFunctionNine.value = _cellFunctionNine.value?.let { it.apply { it.speed = speedValue } } }
        }
        updateSelectedCell()
    }

    fun changeUseColorsFunction(cell: Int, useColorsCount: Int) {
        when(cell) {
            0 -> { _cellFunctionOne.value = _cellFunctionOne.value?.let { it.apply { it.useColors = useColorsCount } } }
            1 -> { _cellFunctionTwo.value = _cellFunctionTwo.value?.let { it.apply { it.useColors = useColorsCount } } }
            2 -> { _cellFunctionThree.value = _cellFunctionThree.value?.let { it.apply { it.useColors = useColorsCount } } }
            3 -> { _cellFunctionFour.value = _cellFunctionFour.value?.let { it.apply { it.useColors = useColorsCount } } }
            4 -> { _cellFunctionFive.value = _cellFunctionFive.value?.let { it.apply { it.useColors = useColorsCount } } }
            5 -> { _cellFunctionSix.value = _cellFunctionSix.value?.let { it.apply { it.useColors = useColorsCount } } }
            6 -> { _cellFunctionSeven.value = _cellFunctionSeven.value?.let { it.apply { it.useColors = useColorsCount } } }
            7 -> { _cellFunctionEight.value = _cellFunctionEight.value?.let { it.apply { it.useColors = useColorsCount } } }
            8 -> { _cellFunctionNine.value = _cellFunctionNine.value?.let { it.apply { it.useColors = useColorsCount } } }
        }
        updateSelectedCell()
    }

    fun changeColorFunction(cell: Int, itemColor: Int, @ColorInt colorValue: Int) {
        when(cell) {
            0 -> { _cellFunctionOne.value = _cellFunctionOne.value?.let { it.apply { it.colorArray[itemColor] = colorValue } } }
            1 -> { _cellFunctionTwo.value = _cellFunctionTwo.value?.let { it.apply { it.colorArray[itemColor] = colorValue } } }
            2 -> { _cellFunctionThree.value = _cellFunctionThree.value?.let { it.apply { it.colorArray[itemColor] = colorValue } } }
            3 -> { _cellFunctionFour.value = _cellFunctionFour.value?.let { it.apply { it.colorArray[itemColor] = colorValue } } }
            4 -> { _cellFunctionFive.value = _cellFunctionFive.value?.let { it.apply { it.colorArray[itemColor] = colorValue } } }
            5 -> { _cellFunctionSix.value = _cellFunctionSix.value?.let { it.apply { it.colorArray[itemColor] = colorValue } } }
            6 -> { _cellFunctionSeven.value = _cellFunctionSeven.value?.let { it.apply { it.colorArray[itemColor] = colorValue } } }
            7 -> { _cellFunctionEight.value = _cellFunctionEight.value?.let { it.apply { it.colorArray[itemColor] = colorValue } } }
            8 -> { _cellFunctionNine.value = _cellFunctionNine.value?.let { it.apply { it.colorArray[itemColor] = colorValue } } }
        }
        updateSelectedCell()
    }

    private fun updateSelectedCell() {
        val codeFunction = _selectedFunction.value?.code
        _selectedFunction.value = _selectedFunction.value

        codeFunction?.let { code ->
            when(code) {
                Constants.WalsFunctionCode.FIRE -> {
                    _blockPreviewFunction.value = true
                    _blockSpinnerThree.value = true
                    _blockLightnessSeekBar.value = true
                    _blockSpeedSeekBar.value = true
                }
                Constants.WalsFunctionCode.HSV -> {
                    _blockLightnessSeekBar.value = true
                    _blockSpeedSeekBar.value = true
                }
                Constants.WalsFunctionCode.FLASH_LIGHT -> {
                    _blockPreviewFunction.value = true
                    _blockSpinnerThree.value = true
                    _blockLightnessSeekBar.value = true
                    _blockSpeedSeekBar.value = true
                }
                Constants.WalsFunctionCode.FADE -> {
                    _blockSpinnerTwo.value = true
                    _blockSpinnerThree.value = true
                    _blockLightnessSeekBar.value = true
                    _blockSpeedSeekBar.value = true
                }
                Constants.WalsFunctionCode.SNAKE -> {
                    _blockSpinnerOne.value = true
                    _blockSpinnerTwo.value = true
                    _blockSpinnerThree.value = true
                    _blockLightnessSeekBar.value = true
                    _blockSpeedSeekBar.value = true
                    _blockPreviewFunction.value = true
                }
                Constants.WalsFunctionCode.PRIDE,
                Constants.WalsFunctionCode.CYLON -> {
                    _blockLightnessSeekBar.value = true
                    _blockSpeedSeekBar.value = true
                }
                else -> {
                    _blockSpinnerOne.value = false
                    _blockSpinnerTwo.value = false
                    _blockSpinnerThree.value = false
                    _blockLightnessSeekBar.value = false
                    _blockSpeedSeekBar.value = false
                    _blockPreviewFunction.value = false
                }
            }
        }
    }

    fun setLightnessIndicator(progress: Int) {
        _lightnessPreview.value = progress
    }

    fun setSpeedIndicator(progress: Int) {
        _speedPreview.value = progress
    }

    private fun generateColorBlack(colorArray: IntArray): IntArray {
        for (i in colorArray.indices) {
            colorArray[i] = Color.BLACK
        }
        return colorArray
    }

    private fun mapFunctionPreset(functionWals: FunctionWals): FunctionWals {
        when (functionWals.code) {
            Constants.WalsFunctionCode.CYLON -> {
                val colorArray = arrayOf(
                    Color.rgb(255, 118, 118),
                    Color.rgb(105, 255, 118),
                    Color.rgb(81, 187, 255)
                )
                functionWals.useColors = 3
                for (i in 0 until functionWals.useColors) {
                    functionWals.colorArray[i] = colorArray[i]
                }
            }
            Constants.WalsFunctionCode.HSV -> {
                val colorArray = arrayOf(
                    Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA
                )
                functionWals.useColors = 6
                for (i in 0 until functionWals.useColors) {
                    functionWals.colorArray[i] = colorArray[i]
                }
            }
            Constants.WalsFunctionCode.FIRE -> {
                val colorArray = arrayOf(
                    Color.RED, Color.rgb(255, 180, 0), Color.WHITE
                )
                functionWals.useColors = 3
                for (i in 0 until functionWals.useColors) {
                    functionWals.colorArray[i] = colorArray[i]
                }
            }
            Constants.WalsFunctionCode.FADE -> {
                val colorArray = arrayOf(
                    Color.RED,
                    Color.BLACK
                )
                functionWals.speed = 1250
                functionWals.useColors = 2
                for (i in 0 until functionWals.useColors) {
                    functionWals.colorArray[i] = colorArray[i]
                }
            }
            Constants.WalsFunctionCode.FLASH_LIGHT -> {
                val colorArray = arrayOf(
                    Color.RED,
                    Color.BLUE
                )
                functionWals.useColors = 2
                functionWals.isSmooth = false
                functionWals.speed = 1000
                for (i in 0 until functionWals.useColors) {
                    functionWals.colorArray[i] = colorArray[i]
                }
            }
            Constants.WalsFunctionCode.PRIDE -> {
                val colorArray = arrayOf(
                    Color.RED,
                    Color.rgb(255, 150, 150),
                    Color.YELLOW,
                    Color.rgb(255, 255, 150),
                    Color.GREEN,
                    Color.CYAN,
                    Color.rgb(150, 255, 255),
                    Color.BLUE
                )
                functionWals.useColors = 8
                for (i in 0 until functionWals.useColors) {
                    functionWals.colorArray[i] = colorArray[i]
                }
            }
        }
        return functionWals
    }

    fun changeFunctionCell(cell: Int, functionCode: Int, functionName: String, functionIcon: Int) {
        val function = mapFunctionPreset(
            FunctionWals(
                code = functionCode,
                name = functionName,
                icon = functionIcon,
                useColors = 2,
                colorArray = generateColorBlack(IntArray(8)),
                isSmooth = true,
                isReverse = false,
                speed = 1,
                lightness = 255
            )
        )
        when(cell) {
            0 -> { _cellFunctionOne.value = function }
            1 -> { _cellFunctionTwo.value = function }
            2 -> { _cellFunctionThree.value = function }
            3 -> { _cellFunctionFour.value = function }
            4 -> { _cellFunctionFive.value = function }
            5 -> { _cellFunctionSix.value = function }
            6 -> { _cellFunctionSeven.value = function }
            7 -> { _cellFunctionEight.value = function }
            8 -> { _cellFunctionNine.value = function }
        }
    }
}