package com.astar.osterrig.controllightmvvm.view.screen_groups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModel
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.FragmentGroupsBinding
import com.astar.osterrig.controllightmvvm.utils.setBackgroundTint
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

internal class GroupListFragment : BaseFragment() {

    private lateinit var mBinding: FragmentGroupsBinding

    override val mModel: ViewModel by viewModel<GroupListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentGroupsBinding.inflate(layoutInflater, container, false)
        initView()
        return mBinding.root
    }

    fun initView() {
        with(mBinding.topNavPanel) {
            btnOne.text = getString(R.string.title_tab_sabers)
            btnTwo.text = getString(R.string.title_tab_groups)
            btnOne.setBackgroundColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.button_active_color,
                    null
                )
            )
            btnOne.setOnClickListener { navigateToDeviceList() }
            btnTwo.setBackgroundTint(R.color.button_active_color)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            GroupListFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}