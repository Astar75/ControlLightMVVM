package com.astar.osterrig.controllightmvvm.view.screen_devices

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.FragmentDevicesBinding
import com.astar.osterrig.controllightmvvm.model.data.AppState
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.service.BleServiceState
import com.astar.osterrig.controllightmvvm.utils.Constants
import com.astar.osterrig.controllightmvvm.utils.setBackgroundTint
import com.astar.osterrig.controllightmvvm.view.MainActivity
import com.astar.osterrig.controllightmvvm.view.adapters.DeviceListAdapter
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

internal class DeviceListFragment : BaseFragment<DeviceListViewModel>() {

    override val mModel: DeviceListViewModel by viewModel()

    private lateinit var binding: FragmentDevicesBinding
    private lateinit var saberListAdapter: DeviceListAdapter

    private var currentDevice: DeviceModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDevicesBinding.inflate(layoutInflater, container, false)

        initViews()
        renderData()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        mModel.loadSabersFromDeviceModel()
    }

    // no use
    private fun checkConnection(device: BluetoothDevice): Boolean {
        return (activity as MainActivity).requestIsConnected(device.address)
    }

    override fun addObserversControl() {
        controlViewModel.connectionState.observe(viewLifecycleOwner, { serviceState ->
            when (serviceState) {
                is BleServiceState.Connected -> {
                    showToastMessage("Device ${serviceState.device.address} connected.")
                    controlViewModel.requestBatteryLevel(serviceState.device)
                    mModel.updateConnectionState(serviceState.device.address, true)
                }
                is BleServiceState.Connecting -> {

                }
                is BleServiceState.Disconnected -> {
                    mModel.updateConnectionState(serviceState.device.address, false)
                    showToastMessage("Device ${serviceState.device.address} disconnected.")
                }
                is BleServiceState.Battery -> {
                    if ((activity as MainActivity).requestIsConnected(serviceState.device.address)) {
                        mModel.updateConnectionState(serviceState.device.address, true)
                        mModel.updateBatteryLevel(
                            serviceState.device.address,
                            serviceState.batteryValue
                        )
                    }
                }
            }
        })
    }

    override fun removeObserversControl() {
        controlViewModel.connectionState.removeObservers(viewLifecycleOwner)
        mModel.subscribe().removeObservers(viewLifecycleOwner)
    }

    private fun initViews() {
        saberListAdapter = DeviceListAdapter()
        saberListAdapter.setOnItemClickListener(saberListAdapterCallback)
        with(binding.topNavPanel) {
            btnOne.text = getString(R.string.title_tab_sabers)
            btnTwo.text = getString(R.string.title_tab_groups)
            btnOne.setBackgroundTint(R.color.button_active_color)
            btnTwo.setOnClickListener { navigateToGroupList() }
        }
        with(binding) {
            recyclerDevices.adapter = saberListAdapter
            recyclerDevices.setHasFixedSize(true)
        }
        binding.btnSearch.setOnClickListener { mModel.searchSabers() }
    }

    private fun renderData() {
        mModel.subscribe().observe(viewLifecycleOwner, { appState ->
            when (appState) {
                is AppState.Success -> {
                    val data = appState.data as MutableList<DeviceModel>
                    val copyData = data.toList()
                    saberListAdapter.addData(copyData)
                    Log.d("DeviceFragment", "renderData: Update List. Count ${data.size}")
                    hideOrShowNoDeviceText(data.size)
                }
                is AppState.Error -> {
                    val data = appState.error
                    showToastMessage("Error: " + data.message)
                }
            }
        })
    }

    private fun hideOrShowNoDeviceText(size: Int) {
        binding.tvNoGroups.visibility = if (size > 0) View.INVISIBLE else View.VISIBLE
    }

    private val saberListAdapterCallback = object : DeviceListAdapter.OnItemClickListener {
        override fun onItemClick(deviceModel: DeviceModel) {
            toSaberControlScreen(deviceModel)
        }

        override fun onItemActionConnectClick(deviceModel: DeviceModel) {
            controlViewModel.connect(arrayListOf(deviceModel))
        }

        override fun onItemActionPowerClick(deviceModel: DeviceModel) {

        }

        override fun onAddSaberToGroup(deviceModel: DeviceModel) {

        }

        override fun onRemoveSaber(deviceModel: DeviceModel) {

        }

        override fun onRenameSaber(deviceModel: DeviceModel) {

        }
    }

    private fun toSaberControlScreen(deviceModel: DeviceModel) {
        if ((activity as MainActivity).requestIsConnected(deviceModel.macAddress)) {
            when (deviceModel.typeSaber) {
                Constants.TypeSaber.TC -> {
                    navigateToTcControl(arrayListOf(deviceModel), true)
                }
                Constants.TypeSaber.RGB,
                Constants.TypeSaber.WALS -> {
                    navigateToRgbControl(arrayListOf(deviceModel), true)
                }
            }
        }
    }

    companion object {

        const val RENAME_SABER_REQUEST_CODE = 1111
        const val MOVE_TO_GROUP_REQUEST_CODE = 1112

        @JvmStatic
        fun newInstance() =
            DeviceListFragment().apply {
                arguments = Bundle().apply {}
            }
    }


}