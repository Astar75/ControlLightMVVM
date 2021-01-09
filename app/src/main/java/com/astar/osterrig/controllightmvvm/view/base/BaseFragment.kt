package com.astar.osterrig.controllightmvvm.view.base

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.astar.osterrig.controllightmvvm.model.data.CctColorEntity
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.view.MainActivity
import com.astar.osterrig.controllightmvvm.view.NavigationManager

internal abstract class BaseFragment<T> : Fragment() {

    protected abstract val mModel: T

    private lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigationManager = (activity as MainActivity).mNavigationManager
    }

    protected fun connect(deviceModel: DeviceModel) {
        (activity as MainActivity).connect(deviceModel)
        // mainViewModel.connect(deviceModel)
    }

    protected fun disconnect(deviceModel: DeviceModel) {
        (activity as MainActivity).disconnect(deviceModel)
        // mainViewModel.disconnect(deviceModel)
    }

    protected fun setLightness(deviceModel: DeviceModel, lightness: Int) {
        (activity as MainActivity).setLightness(deviceModel, lightness)
        // mainViewModel.setLightness(deviceModel, lightness)
    }

    protected fun setColor(deviceModel: DeviceModel, color: Int) {
        (activity as MainActivity).setColor(deviceModel, color)
        // mainViewModel.setColor(deviceModel, color)
    }

    protected fun setColor(deviceModel: DeviceModel, colorModel: CctColorEntity) {
        (activity as MainActivity).setColor(deviceModel, colorModel)
        // mainViewModel.setColor(deviceModel, colorModel)
    }

    protected fun setFunction(deviceModel: DeviceModel, typeSaber: Int, command: String) {
        (activity as MainActivity).setFunction(deviceModel, typeSaber, command)
    }

    protected fun navigateToDeviceList() {
        (activity as MainActivity).mNavigationManager.navigateToDeviceList(false)
        // mainViewModel.navigateToDeviceList(false)
    }

    protected fun navigateToGroupList() {
        (activity as MainActivity).mNavigationManager.navigateToGroupList(false)
        //mainViewModel.navigateToGroupList(false)
    }

    protected fun navigateToRgbControl(deviceModel: DeviceModel, addToBackStack: Boolean) {
        (activity as MainActivity).mNavigationManager.navigateToRgbControl(deviceModel, addToBackStack)
        // mainViewModel.navigateToRgbControl(deviceModel, addToBackStack)
    }

    protected fun navigateToCctControl(deviceModel: DeviceModel) {
        (activity as MainActivity).mNavigationManager.navigateToCctControl(deviceModel, true)
        // mainViewModel.navigateToCctControl(deviceModel, true)
    }

    protected fun navigateToFtpForRgbControl(deviceModel: DeviceModel) {
        (activity as MainActivity).mNavigationManager.navigateToFtpForRgbControl(deviceModel, true)
    }

    protected fun navigateToFtpForWalsControl(deviceModel: DeviceModel) {
        (activity as MainActivity).mNavigationManager.navigateToFtpForWalsControl(deviceModel, true)
        // mainViewModel.navigateToFtpControl(deviceModel, true)
    }

    protected fun navigateToFncControl(deviceModel: DeviceModel) {
        (activity as MainActivity).mNavigationManager.navigateToFncControl(deviceModel, true)
        // mainViewModel.navigateToFncControl(deviceModel, true)
    }

    protected fun navigateToFncRgbControl(deviceModel: DeviceModel) {
        (activity as MainActivity).mNavigationManager.navigateToFncRgbControl(deviceModel, true)
        // mainViewModel.navigateToFncControl(deviceModel, true)
    }

    protected fun showToastMessage(text: String?) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}