package com.astar.osterrig.controllightmvvm.view.base

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.view.MainActivity
import com.astar.osterrig.controllightmvvm.view.MainActivityViewModel
import com.astar.osterrig.controllightmvvm.view.NavigationManager

internal abstract class BaseFragment<T> : Fragment() {

    protected abstract val mModel: T

    protected val controlViewModel: MainActivityViewModel by activityViewModels()

    private lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigationManager = (activity as MainActivity).mNavigationManager
    }

    override fun onStart() {
        super.onStart()
        addObserversControl()
    }

    override fun onStop() {
        super.onStop()
        removeObserversControl()
    }

    protected abstract fun addObserversControl()

    protected abstract fun removeObserversControl()

    protected fun navigateToDeviceList() {
        (activity as MainActivity).mNavigationManager.navigateToDeviceList(false)
        // mainViewModel.navigateToDeviceList(false)
    }

    protected fun navigateToGroupList() {
        (activity as MainActivity).mNavigationManager.navigateToGroupList(false)
        //mainViewModel.navigateToGroupList(false)
    }


    protected fun navigateToTcControl(deviceModel: List<DeviceModel>, addToBackStack: Boolean) {
        (activity as MainActivity).mNavigationManager.navigateToTcControl(deviceModel, addToBackStack)
    }

    protected fun navigateToRgbControl(deviceList: List<DeviceModel>, addToBackStack: Boolean) {
        (activity as MainActivity).mNavigationManager.navigateToRgbControl(deviceList, addToBackStack)
        // mainViewModel.navigateToRgbControl(deviceModel, addToBackStack)
    }

    protected fun navigateToCctControl(deviceList: List<DeviceModel>) {
        (activity as MainActivity).mNavigationManager.navigateToCctControl(deviceList, true)
        // mainViewModel.navigateToCctControl(deviceModel, true)
    }

    protected fun navigateToFtpForRgbControl(deviceList: List<DeviceModel>) {
        (activity as MainActivity).mNavigationManager.navigateToFtpForRgbControl(deviceList, true)
    }

    protected fun navigateToFtpForWalsControl(deviceList: List<DeviceModel>) {
        (activity as MainActivity).mNavigationManager.navigateToFtpForWalsControl(deviceList, true)
        // mainViewModel.navigateToFtpControl(deviceModel, true)
    }

    protected fun navigateToFncControl(deviceList: List<DeviceModel>) {
        (activity as MainActivity).mNavigationManager.navigateToFncControl(deviceList, true)
        // mainViewModel.navigateToFncControl(deviceModel, true)
    }

    protected fun navigateToFncRgbControl(deviceList: List<DeviceModel>) {
        (activity as MainActivity).mNavigationManager.navigateToFncRgbControl(deviceList, true)
        // mainViewModel.navigateToFncControl(deviceModel, true)
    }

    protected fun showToastMessage(text: String?) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}