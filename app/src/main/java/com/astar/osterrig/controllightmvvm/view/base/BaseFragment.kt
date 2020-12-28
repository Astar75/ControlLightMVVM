package com.astar.osterrig.controllightmvvm.view.base

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.view.MainActivity

internal abstract class BaseFragment: Fragment() {

    protected abstract val mModel: ViewModel

    protected fun connect(deviceModel: DeviceModel) {
        Log.d("BaseFragment", "connect")
        (activity as MainActivity).connect(deviceModel)
    }

    protected fun disconnect(deviceModel: DeviceModel) {
        (activity as MainActivity).disconnect(deviceModel)
    }

    protected fun setLightness(deviceModel: DeviceModel, lightness: Int) {
        (activity as MainActivity).setLightness(deviceModel, lightness)
    }

    protected fun setColor(deviceModel: DeviceModel, color: String) {
        (activity as MainActivity).setColor(deviceModel, color)
    }

    protected fun navigateToDeviceList() {
        (activity as MainActivity).navigationManager.navigateToDeviceList(false)
    }

    protected fun navigateToGroupList() {
        (activity as MainActivity).navigationManager.navigateToGroupList(false)
    }

    protected fun navigateToRgbControl(deviceModel: DeviceModel, addToBackStack: Boolean) {
        (activity as MainActivity).navigationManager.navigateToRgbControl(deviceModel, addToBackStack)
    }

    protected fun navigateToCctControl(deviceModel: DeviceModel) {
        (activity as MainActivity).navigationManager.navigateToCctControl(deviceModel, true)
    }

    protected fun navigateToFtpControl(deviceModel: DeviceModel) {
        (activity as MainActivity).navigationManager.navigateToFtpControl(deviceModel, true)
    }

    protected fun navigateToFncControl(deviceModel: DeviceModel) {
        (activity as MainActivity).navigationManager.navigateToFncControl(deviceModel, true)
    }


    protected fun showToastMessage(text: String?) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }


}