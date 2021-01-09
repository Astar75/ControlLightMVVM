package com.astar.osterrig.controllightmvvm.view.viewmodels

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astar.osterrig.controllightmvvm.model.data.CctColorEntity
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel

class ActivityViewModel: ViewModel() {

    val batteryLevelLiveData: MutableLiveData<Pair<BluetoothDevice, Int>> = MutableLiveData()
    val navigateLiveData: MutableLiveData<NavigationScreen> =  MutableLiveData()
    val connectionStateLiveData: MutableLiveData<Pair<DeviceModel, Boolean>> = MutableLiveData()
    val commandLiveData: MutableLiveData<CommandAction> = MutableLiveData()

    fun connect(deviceModel: DeviceModel) {
        connectionStateLiveData.postValue(Pair(deviceModel, true))
    }

    fun disconnect(deviceModel: DeviceModel) {
        connectionStateLiveData.postValue(Pair(deviceModel, false))
    }

    fun setLightness(deviceModel: DeviceModel, lightness: Int) {
        commandLiveData.postValue(CommandAction.SetLightness(deviceModel, lightness))
    }

    fun setColor(deviceModel: DeviceModel, color: Int) {
        commandLiveData.postValue(CommandAction.SetColor(deviceModel, color))
    }

    fun setColor(deviceModel: DeviceModel, colorModel: CctColorEntity) {
        commandLiveData.postValue(CommandAction.SetCctColor(deviceModel, colorModel))
    }

    fun navigateToDeviceList(addToBackStack: Boolean) {
        navigateLiveData.postValue(NavigationScreen.DevicesScreen(addToBackStack))
    }

    fun navigateToGroupList(addToBackStack: Boolean) {
        navigateLiveData.postValue(NavigationScreen.GroupsScreen(addToBackStack))
    }

    fun navigateToRgbControl(deviceModel: DeviceModel, addToBackStack: Boolean) {
        navigateLiveData.postValue(NavigationScreen.RgbControlScreen(deviceModel, addToBackStack))
    }

    fun navigateToCctControl(deviceModel: DeviceModel, addToBackStack: Boolean) {
        navigateLiveData.postValue(NavigationScreen.CctControlScreen(deviceModel, addToBackStack))
    }

    fun navigateToFtpControl(deviceModel: DeviceModel, addToBackStack: Boolean) {
        navigateLiveData.postValue(NavigationScreen.FtpControlScreen(deviceModel, addToBackStack))
    }

    fun navigateToFncControl(deviceModel: DeviceModel, addToBackStack: Boolean) {
        navigateLiveData.postValue(NavigationScreen.FncControlScreen(deviceModel, addToBackStack))
    }

    sealed class CommandAction {
        data class SetLightness(val deviceModel: DeviceModel, val lightness: Int): CommandAction()
        data class SetColor(val deviceModel: DeviceModel, val color: Int): CommandAction()
        data class SetCctColor(val deviceModel: DeviceModel, val color: CctColorEntity): CommandAction()
    }

    sealed class NavigationScreen {
        data class DevicesScreen(val backStack: Boolean): NavigationScreen()
        data class GroupsScreen(val backStack: Boolean): NavigationScreen()
        data class RgbControlScreen(val deviceModel: DeviceModel, val backStack: Boolean): NavigationScreen()
        data class CctControlScreen(val deviceModel: DeviceModel, val backStack: Boolean): NavigationScreen()
        data class FtpControlScreen(val deviceModel: DeviceModel, val backStack: Boolean): NavigationScreen()
        data class FncControlScreen(val deviceModel: DeviceModel, val backStack: Boolean): NavigationScreen()
    }
}