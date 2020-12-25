package com.astar.osterrig.controllightmvvm.view.screen_devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.astar.osterrig.controllightmvvm.databinding.FragmentDevicesBinding
import com.astar.osterrig.controllightmvvm.model.data.AppState
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
        renderData()
        return mBinding.root
    }

    private fun initViews() {
        mBinding.btnSearch.setOnClickListener { startScan() }
    }

    private fun startScan() {
        mModel.startScan()
    }

    private fun renderData() {
        mModel.subscribe().observe(viewLifecycleOwner, { appState ->
            when(appState) {
                is AppState.Success -> {
                    val data = appState.data
                    var string = ""
                    for (dev in data) {
                        string += "Name: ${dev.name}, address: ${dev.macAddress}\n"
                    }
                    showToastMessage(string)
                }
                is AppState.Error -> {
                    val data = appState.error
                    showToastMessage(data.message)
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DeviceListFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}