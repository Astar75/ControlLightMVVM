package com.astar.osterrig.controllightmvvm.view.screen_devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.FragmentDevicesBinding
import com.astar.osterrig.controllightmvvm.model.data.AppState
import com.astar.osterrig.controllightmvvm.view.adapters.DeviceListAdapter
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

internal class DeviceListFragment : BaseFragment() {

    private lateinit var mBinding: FragmentDevicesBinding
    private val mModel: DeviceListViewModel by viewModel()
    private lateinit var mAdapter: DeviceListAdapter

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
        mBinding.topNavPanel.btnOne.text = getString(R.string.title_tab_sabers)
        mBinding.topNavPanel.btnTwo.text = getString(R.string.title_tab_groups)
        mAdapter = DeviceListAdapter()
        mBinding.recyclerDevices.adapter = mAdapter
        mBinding.recyclerDevices.addItemDecoration(
            DividerItemDecoration(
                context,
                RecyclerView.VERTICAL
            )
        )
        mBinding.btnSearch.setOnClickListener { startScan() }
        mBinding.topNavPanel.btnOne.highlightColor = context.getColor(R.color.button_active_color)
        mBinding.topNavPanel.btnOne.setOnClickListener { navigateToDeviceList() }
        mBinding.topNavPanel.btnTwo.setOnClickListener { navigateToGroupList() }
    }

    private fun startScan() {
        mModel.startScan()
    }

    private fun renderData() {
        mModel.subscribe().observe(viewLifecycleOwner, { appState ->
            when (appState) {
                is AppState.Success -> {
                    val data = appState.data
                    mAdapter.addData(data)
                    hideOrShowNoDeviceText(data.size)
                }
                is AppState.Error -> {
                    val data = appState.error
                    showToastMessage(data.message)
                }
            }
        })
    }

    private fun hideOrShowNoDeviceText(size: Int) {
        mBinding.tvNoDevice.visibility = if (size > 0) View.INVISIBLE else View.VISIBLE
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DeviceListFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}