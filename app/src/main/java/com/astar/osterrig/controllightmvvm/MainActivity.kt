package com.astar.osterrig.controllightmvvm

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.astar.osterrig.controllightmvvm.databinding.ActivityMainBinding
import com.astar.osterrig.controllightmvvm.model.data.AppState
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.view.BaseActivity
import com.astar.osterrig.controllightmvvm.view.MainInteractor
import com.astar.osterrig.controllightmvvm.view.MainViewModel
import com.astar.osterrig.controllightmvvm.view.dialogs.AddDeviceDialog


class MainActivity : BaseActivity<AppState, MainInteractor>() {

    override val model: MainViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
    }

    private val observer = Observer <AppState> { renderData(it) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnByMac.setOnClickListener { getDeviceByMacAddress() }
        binding.btnGetAllDevices.setOnClickListener { getAllDevices() }
        binding.btnAddDevice.setOnClickListener { openAddDeviceDialog() }
    }

    private fun getDeviceByMacAddress() {
        val mac = binding.etMacAddress.text.toString()
        if (mac.isNotEmpty()) {
            model.getDeviceByMacAddress(mac).observe(this, observer)
        }
    }

    private fun getAllDevices() {
        model.getDevices().observe(this, observer)
    }

    private fun openAddDeviceDialog() {
        val dialog = AddDeviceDialog.newInstance()
        dialog.show(supportFragmentManager, "add_dialog")
        dialog.addCallback(object : AddDeviceDialog.CallbackListener {

            override fun onClick(device: DeviceModel) {
                model.addDevice(device)
            }
        })
    }

    override fun renderData(dataModel: AppState) {
        when(dataModel) {
            is AppState.Success<*> -> {
                val data = dataModel.data
                if (data is DeviceModel) {
                    binding.tvDevices.text = "Name: ${data.name}, Mac: ${data.macAddress}, Group: ${data.groupName}"
                } else if (data is List<*>) {
                    val devices = data as List<DeviceModel>
                    var strForTextView = String()
                    for (device in devices) {
                        strForTextView += String.format("Name Device: %s, Mac: %s, Group: %s\n", device.name, device.macAddress, device.groupName)
                    }
                    binding.tvDevices.text = strForTextView
                }
            }
            is AppState.Loading -> {
                Toast.makeText(this, "Загрузка данных...", Toast.LENGTH_SHORT).show()
            }
            is AppState.Error -> {
                Toast.makeText(this, dataModel.error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


}