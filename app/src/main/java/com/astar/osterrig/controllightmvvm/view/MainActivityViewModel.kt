package com.astar.osterrig.controllightmvvm.view

import android.bluetooth.BluetoothDevice
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astar.osterrig.controllightmvvm.model.data.CctColorEntity
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.model.data.FunctionWals
import com.astar.osterrig.controllightmvvm.service.BleServiceState
import com.astar.osterrig.controllightmvvm.utils.Constants.*
import com.astar.osterrig.controllightmvvm.utils.colorIntToRgbString
import java.lang.StringBuilder

class MainActivityViewModel : ViewModel() {

    var connectionState: MutableLiveData<BleServiceState> = MutableLiveData()

    var connect: MutableLiveData<Pair<List<DeviceModel>, Boolean>> = MutableLiveData()
    var lightness: MutableLiveData<Pair<List<DeviceModel>, Int>> = MutableLiveData()
    var speed: MutableLiveData<Pair<List<DeviceModel>, Int>> = MutableLiveData()
    var color: MutableLiveData<Pair<List<DeviceModel>, Int>> = MutableLiveData()
    var colorTc: MutableLiveData<Triple<List<DeviceModel>, Int, Int>> = MutableLiveData()
    var cctColor: MutableLiveData<Pair<List<DeviceModel>, CctColorEntity>> = MutableLiveData()
    var function: MutableLiveData<Triple<List<DeviceModel>, Int, String>> = MutableLiveData()
    var batteryLevel: MutableLiveData<BluetoothDevice> = MutableLiveData()

    fun setConnectionState(bleServiceState: BleServiceState) {
        connectionState.value = bleServiceState
    }

    fun connect(deviceList: List<DeviceModel>) {
        connect.value = deviceList to true
    }

    fun disconnect(deviceModel: List<DeviceModel>) {
        connect.value = deviceModel to false
    }

    fun setLightness(deviceModel: List<DeviceModel>, value: Int) {
        lightness.value = deviceModel to value
    }

    fun setSpeed(deviceModel: List<DeviceModel>, value: Int) {
        speed.value = deviceModel to value
    }

    fun setColor(deviceModel: List<DeviceModel>, warm: Int, cold: Int) {
        colorTc.value = Triple(deviceModel, warm, cold)
    }

    fun setColor(deviceModel: List<DeviceModel>, colorValue: Int) {
        color.value = deviceModel to colorValue
    }

    fun setColor(deviceModel: List<DeviceModel>, red: Int, green: Int, blue: Int) {
        color.value = deviceModel to Color.rgb(red, green, blue)
    }


    fun setCctColor(deviceModel: List<DeviceModel>, value: CctColorEntity) {
        cctColor.value = deviceModel to value
    }

    fun setTint(deviceModel: List<DeviceModel>, tintValue: Int, colorValue: Pair<List<DeviceModel>, CctColorEntity>) {
        val currentColor = colorValue.second
        var red = currentColor.red
        red -= tintValue
        val newCctColor = CctColorEntity(
            kelvin = currentColor.kelvin,
            backgroundCell = currentColor.backgroundCell,
            red = red,
            green = currentColor.green,
            blue = currentColor.blue,
            white = currentColor.white)
        cctColor.value = deviceModel to newCctColor
    }

    fun setFunction(deviceModel: List<DeviceModel>, typeSaber: Int, command: String) {
        function.value = Triple(deviceModel, typeSaber, command)
    }

    fun setFunction(deviceModel: List<DeviceModel>, typeSaber: Int, functionWals: FunctionWals) {
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
                        buildCommand.append("c${i + 1}=${colorIntToRgbString(functionWals.colorArray[i])};")
                    }
                }
                WalsFunctionCode.FLASH_LIGHT -> {
                    buildCommand.append("c=${functionWals.code};")
                    buildCommand.append(if (functionWals.isReverse) "d=r;" else "d=l;")
                    buildCommand.append(if (functionWals.isSmooth) "t=sm;" else "t=sh;")
                    buildCommand.append("cc=2;")
                    for (i in 0 until 2) {
                        buildCommand.append("c${i + 1}=${colorIntToRgbString(functionWals.colorArray[i])};")
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
                    buildCommand.append("f_c=${functionWals.cooling};")
                    buildCommand.append("f_s=${functionWals.sparking};")
                }
            }
            buildCommand.append("ls=${functionWals.lightness};")  // яркость
            buildCommand.append("sp=${functionWals.speed};")      // скорость

            Log.d("MainActivityViewModel", "setFunction: $buildCommand")
            function.value = Triple(deviceModel, typeSaber, buildCommand.toString())
        }
    }

    fun requestBatteryLevel(device: BluetoothDevice) {
        batteryLevel.value = device
    }

    // TODO: 15.01.2021 Добавить включение груп для функций

    override fun onCleared() {
        super.onCleared()
    }

}