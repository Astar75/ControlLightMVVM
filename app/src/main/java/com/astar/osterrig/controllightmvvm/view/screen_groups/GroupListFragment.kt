package com.astar.osterrig.controllightmvvm.view.screen_groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.FragmentGroupsBinding
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment

internal class GroupListFragment : BaseFragment() {

    private lateinit var mBinding: FragmentGroupsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentGroupsBinding.inflate(layoutInflater, container, false)
        initViews()
        return mBinding.root
    }

    private fun initViews() {
        mBinding.topNavPanel.btnOne.text = getString(R.string.title_tab_sabers)
        mBinding.topNavPanel.btnTwo.text = getString(R.string.title_tab_groups)

        mBinding.topNavPanel.btnOne.setBackgroundColor(
            ResourcesCompat.getColor(
                resources,
                R.color.button_active_color,
                null
            )
        )
        mBinding.topNavPanel.btnOne.setOnClickListener { navigateToDeviceList() }
        mBinding.topNavPanel.btnTwo.setOnClickListener { navigateToGroupList() }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            GroupListFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}