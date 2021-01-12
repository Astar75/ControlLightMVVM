package com.astar.osterrig.controllightmvvm.view.screen_fnc_rgb_control

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astar.osterrig.controllightmvvm.model.data.FunctionRgb
import com.astar.osterrig.controllightmvvm.utils.Constants.RgbFunctionCode
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

    private val _functionPreview: MutableLiveData<Pair<Boolean, IntArray>> = MutableLiveData()
    val functionPreview: LiveData<Pair<Boolean, IntArray>>
        get() = _functionPreview

    init {
        val functionsData = DataProvider.getPresetRgbFunctions()

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

    fun setFunctionToCell(cell: Int, code: Int, name: String, icon: Int) {
        when (cell) {
            0 -> {
                _cellFunctionOne.value =
                    _cellFunctionOne.value?.let { applyChangeFunction(it, code, name, icon) }
            }
            1 -> {
                _cellFunctionTwo.value =
                    _cellFunctionTwo.value?.let { applyChangeFunction(it, code, name, icon) }
            }
            2 -> {
                _cellFunctionThree.value =
                    _cellFunctionThree.value?.let { applyChangeFunction(it, code, name, icon) }
            }
            3 -> {
                _cellFunctionFour.value =
                    _cellFunctionFour.value?.let { applyChangeFunction(it, code, name, icon) }
            }
            4 -> {
                _cellFunctionFive.value =
                    _cellFunctionFive.value?.let { applyChangeFunction(it, code, name, icon) }
            }
            5 -> {
                _cellFunctionSix.value =
                    _cellFunctionSix.value?.let { applyChangeFunction(it, code, name, icon) }
            }
            6 -> {
                _cellFunctionSeven.value =
                    _cellFunctionSeven.value?.let { applyChangeFunction(it, code, name, icon) }
            }
            7 -> {
                _cellFunctionEight.value =
                    _cellFunctionEight.value?.let { applyChangeFunction(it, code, name, icon) }
            }
            8 -> {
                _cellFunctionNine.value =
                    _cellFunctionNine.value?.let { applyChangeFunction(it, code, name, icon) }
            }
        }
    }

    private fun applyChangeFunction(
        currentFunction: FunctionRgb,
        code: Int, name: String, icon: Int
    ): FunctionRgb {
        currentFunction.code = code
        currentFunction.name = name
        currentFunction.icon = icon
        return currentFunction
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

        setFunctionPreview()
    }

    private fun setFunctionPreview() {
        val functionCode = _selectedFunction.value
        functionCode?.code.let {
            when (it) {
                RgbFunctionCode.NONE -> {
                    _functionPreview.value = false to intArrayOf(Color.BLACK, Color.BLACK)
                }
                RgbFunctionCode.CROSS_FADE -> {
                    _functionPreview.value = true to intArrayOf(
                        Color.RED,
                        Color.YELLOW,
                        Color.GREEN,
                        Color.CYAN,
                        Color.BLUE,
                        Color.MAGENTA
                    )
                }
                RgbFunctionCode.RED_FADE -> {
                    _functionPreview.value = true to intArrayOf(Color.RED, Color.BLACK)
                }
                RgbFunctionCode.YELLOW_FADE -> {
                    _functionPreview.value = true to intArrayOf(Color.YELLOW, Color.BLACK)
                }
                RgbFunctionCode.GREEN_FADE -> {
                    _functionPreview.value = true to intArrayOf(Color.GREEN, Color.BLACK)
                }
                RgbFunctionCode.CYAN_FADE -> {
                    _functionPreview.value = true to intArrayOf(Color.CYAN, Color.BLACK)
                }
                RgbFunctionCode.BLUE_FADE -> {
                    _functionPreview.value = true to intArrayOf(Color.BLUE, Color.BLACK)
                }
                RgbFunctionCode.PURPLE_FADE -> {
                    _functionPreview.value = true to intArrayOf(Color.MAGENTA, Color.BLACK)
                }
                RgbFunctionCode.RED_BLUE_FADE -> {
                    _functionPreview.value = true to intArrayOf(Color.RED, Color.BLUE)
                }
                RgbFunctionCode.RED_GREEN_FADE -> {
                    _functionPreview.value = true to intArrayOf(Color.RED, Color.GREEN)
                }
                RgbFunctionCode.GREEN_BLUE_FADE -> {
                    _functionPreview.value = true to intArrayOf(Color.GREEN, Color.BLUE)
                }
                RgbFunctionCode.POLICE -> {
                    _functionPreview.value = false to intArrayOf(Color.RED, Color.BLUE)
                }
                RgbFunctionCode.STROBE_FLASH -> {
                    _functionPreview.value = false to intArrayOf(
                        Color.RED,
                        Color.BLACK,
                        Color.YELLOW,
                        Color.BLACK,
                        Color.GREEN,
                        Color.BLACK,
                        Color.CYAN,
                        Color.BLACK,
                        Color.BLUE,
                        Color.BLACK,
                        Color.MAGENTA,
                        Color.BLACK
                    )
                }
                RgbFunctionCode.RED_STROBE_FLASH -> {
                    _functionPreview.value = false to intArrayOf(Color.RED, Color.BLACK)
                }
                RgbFunctionCode.GREEN_STROBE_FLASH -> {
                    _functionPreview.value = false to intArrayOf(Color.GREEN, Color.BLACK)
                }
                RgbFunctionCode.BLUE_STROBE_FLASH -> {
                    _functionPreview.value = false to intArrayOf(Color.BLUE, Color.BLACK)
                }
                RgbFunctionCode.YELLOW_STROBE_FLASH -> {
                    _functionPreview.value = false to intArrayOf(Color.YELLOW, Color.BLACK)
                }
                RgbFunctionCode.PURPLE_STROBE_FLASH -> {
                    _functionPreview.value = false to intArrayOf(Color.MAGENTA, Color.BLACK)
                }
                RgbFunctionCode.CYAN_STROBE_FLASH -> {
                    _functionPreview.value = false to intArrayOf(Color.CYAN, Color.BLACK)
                }
                RgbFunctionCode.JUMPING_CHANGE -> {
                    _functionPreview.value = false to intArrayOf(
                        Color.RED,
                        Color.YELLOW,
                        Color.GREEN,
                        Color.CYAN,
                        Color.BLUE,
                        Color.MAGENTA
                    )
                }
                RgbFunctionCode.BROKEN_LAMP_1 -> {
                    _functionPreview.value =
                        false to intArrayOf(Color.WHITE, Color.BLACK, Color.WHITE)
                }
                RgbFunctionCode.BROKEN_LAMP_2 -> {
                    _functionPreview.value = false to intArrayOf(
                        Color.WHITE,
                        Color.BLACK,
                        Color.WHITE,
                        Color.WHITE,
                        Color.BLACK,
                        Color.WHITE
                    )
                }
                RgbFunctionCode.BROKEN_LAMP_3 -> {
                    _functionPreview.value = false to intArrayOf(
                        Color.YELLOW,
                        Color.WHITE,
                        Color.BLACK,
                        Color.WHITE,
                        Color.BLACK,
                        Color.YELLOW
                    )
                }
                RgbFunctionCode.BROKEN_LAMP_4 -> {
                    _functionPreview.value = false to intArrayOf(
                        Color.YELLOW,
                        Color.BLACK,
                        Color.YELLOW,
                        Color.YELLOW,
                        Color.BLACK,
                        Color.YELLOW
                    )
                }
                RgbFunctionCode.BROKEN_LAMP_5 -> {
                    _functionPreview.value = false to intArrayOf(
                        Color.YELLOW,
                        Color.BLACK,
                        Color.YELLOW,
                        Color.BLACK,
                        Color.YELLOW,
                        Color.BLACK
                    )
                }
            }
        }
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
        _lightnessPreview.value = progress
    }

    fun setSpeed(progress: Int) {
        _speedPreview.value = progress
    }
}