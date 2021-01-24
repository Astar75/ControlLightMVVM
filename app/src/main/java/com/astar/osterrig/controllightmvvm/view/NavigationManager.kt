package com.astar.osterrig.controllightmvvm.view

import androidx.fragment.app.FragmentManager
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel

interface NavigationManager {

    fun attachManager(fragmentManager: FragmentManager)
    fun detachManager()
    fun navigateToDeviceList(addToBackStack: Boolean)
    fun navigateToGroupList(addToBackStack: Boolean)
    fun navigateToTcControl(deviceModel: List<DeviceModel>, addToBackStack: Boolean)
    fun navigateToRgbControl(deviceModel: List<DeviceModel>, addToBackStack: Boolean)
    fun navigateToCctControl(deviceModel: List<DeviceModel>, addToBackStack: Boolean)
    fun navigateToFtpForWalsControl(deviceModel: List<DeviceModel>, addToBackStack: Boolean)
    fun navigateToFncControl(deviceModel: List<DeviceModel>, addToBackStack: Boolean)
    fun navigateToFncRgbControl(deviceModel: List<DeviceModel>, addToBackStack: Boolean)
    fun navigateToFtpForRgbControl(deviceModel: List<DeviceModel>, addToBackStack: Boolean)
}