package com.astar.osterrig.controllightmvvm.view.screen_devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.FragmentDevicesBinding
import com.astar.osterrig.controllightmvvm.model.data.AppState
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.utils.setBackgroundTint
import com.astar.osterrig.controllightmvvm.view.adapters.DeviceListAdapter
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

internal class DeviceListFragment : BaseFragment() {

    override val mModel: DeviceListViewModel by viewModel()

    private lateinit var mBinding: FragmentDevicesBinding
    private lateinit var mAdapter: DeviceListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentDevicesBinding.inflate(layoutInflater, container, false)
        initView()
        renderData()
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        mAdapter.setOnItemClickListener(adapterItemClickListener)
    }

    override fun onStop() {
        super.onStop()
        mAdapter.removeOnItemClickListener()
    }

    private val adapterItemClickListener = object : DeviceListAdapter.OnItemClickListener {
        override fun onItemClick(deviceModel: DeviceModel) {
            navigateToRgbControl(deviceModel, true)
        }

        override fun onItemActionConnectClick(deviceModel: DeviceModel) {
            showToastMessage("Action connect")
        }

        override fun onItemActionPowerClick(deviceModel: DeviceModel) {
            showToastMessage("Action power")
        }
    }

    private fun initView() {
        mAdapter = DeviceListAdapter()
        with(mBinding.topNavPanel) {
            btnOne.text = getString(R.string.title_tab_sabers)
            btnTwo.text = getString(R.string.title_tab_groups)
            btnOne.setBackgroundTint(R.color.button_active_color)
            btnTwo.setOnClickListener { navigateToGroupList() }
        }
        with(mBinding) {
            recyclerDevices.adapter = mAdapter
            recyclerDevices.addItemDecoration(
                DividerItemDecoration(
                    context,
                    RecyclerView.VERTICAL
                )
            )
            btnSearch.setOnClickListener { startScan() }
        }
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