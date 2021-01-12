package com.astar.osterrig.controllightmvvm.view

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astar.osterrig.controllightmvvm.model.data.CctColorEntity
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.model.data.FunctionWals
import com.astar.osterrig.controllightmvvm.service.BleServiceState
import com.astar.osterrig.controllightmvvm.utils.Constants
import com.astar.osterrig.controllightmvvm.utils.Constants.*
import com.astar.osterrig.controllightmvvm.utils.colorIntToRgbString
import java.lang.StringBuilder

class MainActivityViewModel : ViewModel() {

    var connectionState: MutableLiveData<BleServiceState> = MutableLiveData()

    var connect: MutableLiveData<Pair<DeviceModel, Boolean>> = MutableLiveData()
    var lightness: MutableLiveData<Pair<DeviceModel, Int>> = MutableLiveData()
    var speed: MutableLiveData<Pair<DeviceModel, Int>> = MutableLiveData()
    var color: MutableLiveData<Pair<DeviceModel, Int>> = MutableLiveData()
    var cctColor: MutableLiveData<Pair<DeviceModel, CctColorEntity>> = MutableLiveData()
    var function: MutableLiveData<Triple<DeviceModel, Int, String>> = MutableLiveData()

    fun setConnectionState(bleServiceState: BleServiceState) {
        connectionState.value = bleServiceState
    }

    fun connect(deviceModel: DeviceModel) {
        connect.value = deviceModel to true
    }

    fun disconnect(deviceModel: DeviceModel) {
        connect.value = deviceModel to false
    }

    fun setLightness(deviceModel: DeviceModel, value: Int) {
        lightness.value = deviceModel to value
    }

    fun setSpeed(deviceModel: DeviceModel, value: Int) {
        speed.value = deviceModel to value
    }

    fun setColor(deviceModel: DeviceModel, colorValue: Int) {
        color.value = deviceModel to colorValue
    }

    fun setColor(deviceModel: DeviceModel, red: Int, green: Int, blue: Int) {
        color.value = deviceModel to Color.rgb(red, green, blue)
    }

    fun setCctColor(deviceModel: DeviceModel, value: CctColorEntity) {
        cctColor.value = deviceModel to value
    }


    fun setTint(deviceModel: DeviceModel, tintValue: Int, colorValue: Int) {
        var red = Color.red(colorValue)
        val green = Color.green(colorValue)
        val blue = Color.blue(colorValue)
        // TODO: 09.01.2021 вычислить tint
        red -= tintValue
        val result = Color.rgb(red, green, blue)
        color.value = deviceModel to result
    }

    fun setFunction(deviceModel: DeviceModel, typeSaber: Int, command: String) {
        function.value = Triple(deviceModel, typeSaber, command)
    }

    fun setFunction(deviceModel: DeviceModel, typeSaber: Int, functionWals: FunctionWals) {
        if (typeSaber == TypeSaber.WALS) {
            val buildCommand = StringBuilder()
            when (functionWals.code) {
                WalsFunctionCode.HSV,
                WalsFunctionCode.PRIDE,
                WalsFunctionCode.CYLON,
                WalsFunctionCode.BROKEN_LAMP -> {
                    buildCommand.append("c=${functionWals.code};")
                }
                WalsFunctionCode.SNAKE -> {
                    buildCommand.append("c=${functionWals.code};")
                    buildCommand.append(if (functionWals.isReverse) "d=r;" else "d=l;")
                    buildCommand.append(if (functionWals.isSmooth) "t=sm;" else "t=sh;")
                    buildCommand.append("cc=${functionWals.useColors};")
                    for (i in 0 until functionWals.useColors) {
                        buildCommand.append("c${i+1}=${colorIntToRgbString(functionWals.colorArray[i])};")
                    }
                }
                WalsFunctionCode.FLASH_LIGHT -> {
                    buildCommand.append("c=${functionWals.code};")
                    buildCommand.append(if (functionWals.isReverse) "d=r;" else "d=l;")
                    buildCommand.append(if (functionWals.isSmooth) "t=sm;" else "t=sh;")
                    buildCommand.append("cc=2;")
                    for (i in 0 until 2) {
                        buildCommand.append("c${i+1}=${colorIntToRgbString(functionWals.colorArray[i])};")
                    }
                }
                WalsFunctionCode.FADE -> {
                    if (functionWals.isSmooth) {
                        buildCommand.append("c=131;")
                    } else {
                        buildCommand.append("c=${functionWals.code};")
                    }
                    buildCommand.append("cc=2;")
                    for (i in 0 until 2) {
                        buildCommand.append("c${i + 1}=${colorIntToRgbString(functionWals.colorArray[i])};")
                    }
                }
                WalsFunctionCode.FIRE -> {
                    buildCommand.append("c=${functionWals.code};")
                    buildCommand.append(if (functionWals.isReverse) "d=r;" else "d=l;")
                    buildCommand.append("f_c=60;")
                    buildCommand.append("f_s=100;")
                }
            }
            buildCommand.append("ls=${functionWals.lightness};")  // яркость
            buildCommand.append("sp=${functionWals.speed};")      // скорость

            Log.d("MainActivityViewModel", "setFunction: ${buildCommand.toString()}")
            function.value = Triple(deviceModel, typeSaber, buildCommand.toString())
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

}