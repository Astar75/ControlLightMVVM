package com.astar.osterrig.controllightmvvm.view.screen_groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        return mBinding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            GroupListFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}