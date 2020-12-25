package com.astar.osterrig.controllightmvvm.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.view.screen_devices.DeviceListFragment
import com.astar.osterrig.controllightmvvm.view.screen_groups.GroupListFragment
import com.astar.osterrig.controllightmvvm.view.screen_rgb_control.RgbControlFragment

internal class NavigationManager {

    private var mFragmentManager: FragmentManager? = null
    private var mCurrentFragment: Fragment? = null

    fun attachManager(fragmentManager: FragmentManager) {
        mFragmentManager = fragmentManager
    }

    fun detachManager() {
        mFragmentManager = null
        mCurrentFragment = null
    }

    private fun navigate(fragment: Fragment) {
        mFragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentContainer, fragment)
            ?.commit()
    }

    fun navigateToDeviceList() {
        navigate(DeviceListFragment.newInstance())
    }

    fun navigateToGroupList() {
        navigate(GroupListFragment.newInstance())
    }

    fun navigateToRgbControl(color: Int, lightness: Int) {
        navigate(RgbControlFragment.newInstance(color, lightness))
    }

    companion object {
        fun newInstance(): NavigationManager {
            return NavigationManager()
        }
    }

}