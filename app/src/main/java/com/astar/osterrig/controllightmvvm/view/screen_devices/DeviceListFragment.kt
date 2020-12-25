package com.astar.osterrig.controllightmvvm.view.screen_devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.astar.osterrig.controllightmvvm.databinding.FragmentDevicesBinding
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

internal class DeviceListFragment : BaseFragment() {
    
    private lateinit var mBinding: FragmentDevicesBinding
    private val mModel: DeviceListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentDevicesBinding.inflate(layoutInflater, container, false)
        initViews()
        return mBinding.root
    }

    private fun initViews() {
        mBinding.btnSearch.setOnClickListener { startScan() }
    }

    private fun startScan() {
        mModel.startScan()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DeviceListFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}