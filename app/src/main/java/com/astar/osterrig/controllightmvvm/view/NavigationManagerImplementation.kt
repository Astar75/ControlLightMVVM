package com.astar.osterrig.controllightmvvm.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.view.screen_cct_control.CctControlFragment
import com.astar.osterrig.controllightmvvm.view.screen_devices.DeviceListFragment
import com.astar.osterrig.controllightmvvm.view.screen_fnc_control.FncControlFragment
import com.astar.osterrig.controllightmvvm.view.screen_ftp_control.FtpControlFragment
import com.astar.osterrig.controllightmvvm.view.screen_groups.GroupListFragment
import com.astar.osterrig.controllightmvvm.view.screen_rgb_control.RgbControlFragment

internal class NavigationManagerImplementation: NavigationManager {

    private var mFragmentManager: FragmentManager? = null
    private var mCurrentFragment: Fragment? = null

    override fun attachManager(fragmentManager: FragmentManager) {
        mFragmentManager = fragmentManager
    }

    override fun detachManager() {
        mFragmentManager = null
        mCurrentFragment = null
    }

    override fun navigateToDeviceList(addToBackStack: Boolean) {
        navigate(DeviceListFragment.newInstance(), addToBackStack)
    }

    override fun navigateToGroupList(addToBackStack: Boolean) {
        navigate(GroupListFragment.newInstance(), addToBackStack)
    }

    override fun navigateToRgbControl(deviceModel: DeviceModel, addToBackStack: Boolean) {
        navigate(RgbControlFragment.newInstance(deviceModel), addToBackStack)
    }

    override fun navigateToCctControl(deviceModel: DeviceModel, addToBackStack: Boolean) {
        navigate(CctControlFragment.newInstance(deviceModel), addToBackStack)
    }

    override fun navigateToFtpControl(deviceModel: DeviceModel, addToBackStack: Boolean) {
        navigate(FtpControlFragment.newInstance(), addToBackStack)
    }

    override fun navigateToFncControl(deviceModel: DeviceModel, addToBackStack: Boolean) {
        navigate(FncControlFragment.newInstance(), addToBackStack)
    }

    private fun navigate(fragment: Fragment, addToBackStack: Boolean) {
        with(mFragmentManager?.beginTransaction()) {
            if (addToBackStack) this?.addToBackStack(null)
            this?.replace(R.id.fragmentContainer, fragment)
            this?.commitAllowingStateLoss()
        }
    }

    companion object {
        fun newInstance(): NavigationManagerImplementation {
            return NavigationManagerImplementation()
        }
    }
}