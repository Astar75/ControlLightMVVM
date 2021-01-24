package com.astar.osterrig.controllightmvvm.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.view.screen_cct_control.CctControlFragment
import com.astar.osterrig.controllightmvvm.view.screen_devices.DeviceListFragment
import com.astar.osterrig.controllightmvvm.view.screen_fnc_wals_control.FncWalsControlFragment
import com.astar.osterrig.controllightmvvm.view.screen_fnc_rgb_control.FncRgbControlFragment
import com.astar.osterrig.controllightmvvm.view.screen_ftp_control.FtpControlFragment
import com.astar.osterrig.controllightmvvm.view.screen_ftp_rgb_control.FtpRgbControlFragment
import com.astar.osterrig.controllightmvvm.view.screen_groups.GroupListFragment
import com.astar.osterrig.controllightmvvm.view.screen_rgb_control.RgbControlFragment
import com.astar.osterrig.controllightmvvm.view.screen_tc_control.TcControlFragment

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

    override fun navigateToTcControl(deviceModel: List<DeviceModel>, addToBackStack: Boolean) {
        navigate(TcControlFragment.newInstance(ArrayList(deviceModel)), addToBackStack)
    }

    override fun navigateToRgbControl(deviceModel: List<DeviceModel>, addToBackStack: Boolean) {
        navigate(RgbControlFragment.newInstance(ArrayList(deviceModel)), addToBackStack)
    }

    override fun navigateToCctControl(deviceModel: List<DeviceModel>, addToBackStack: Boolean) {
        navigate(CctControlFragment.newInstance(ArrayList(deviceModel)), addToBackStack)
    }

    override fun navigateToFtpForRgbControl(deviceModel: List<DeviceModel>, addToBackStack: Boolean) {
        navigate(FtpRgbControlFragment.newInstance(ArrayList(deviceModel)), addToBackStack)
    }

    override fun navigateToFtpForWalsControl(deviceModel: List<DeviceModel>, addToBackStack: Boolean) {
        navigate(FtpControlFragment.newInstance(ArrayList(deviceModel)), addToBackStack)
    }

    override fun navigateToFncControl(deviceModel: List<DeviceModel>, addToBackStack: Boolean) {
        navigate(FncWalsControlFragment.newInstance(ArrayList(deviceModel)), addToBackStack)
    }

    override fun navigateToFncRgbControl(deviceModel: List<DeviceModel>, addToBackStack: Boolean) {
        navigate(FncRgbControlFragment.newInstance(ArrayList(deviceModel)), addToBackStack)
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