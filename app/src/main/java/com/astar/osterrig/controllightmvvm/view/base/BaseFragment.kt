package com.astar.osterrig.controllightmvvm.view.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.astar.osterrig.controllightmvvm.view.MainActivity

internal abstract class BaseFragment : Fragment() {

    protected fun navigateToDeviceList() {
        (activity as MainActivity).navigationManager.navigateToDeviceList()
    }

    protected fun navigateToGroupList() {
        (activity as MainActivity).navigationManager.navigateToGroupList()
    }

    protected fun showToastMessage(text: String?) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}