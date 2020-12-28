package com.astar.osterrig.controllightmvvm.view

import androidx.fragment.app.FragmentManager
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel

interface NavigationManager {

    fun attachManager(fragmentManager: FragmentManager)
    fun detachManager()
    fun navigateToDeviceList(addToBackStack: Boolean)
    fun navigateToGroupList(addToBackStack: Boolean)
    fun navigateToRgbControl(deviceModel: DeviceModel, addToBackStack: Boolean)
    fun navigateToCctControl(deviceModel: DeviceModel, addToBackStack: Boolean)
    fun navigateToFtpControl(deviceModel: DeviceModel, addToBackStack: Boolean)
    fun navigateToFncControl(deviceModel: DeviceModel, addToBackStack: Boolean)
}