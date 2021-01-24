package com.astar.osterrig.controllightmvvm.view.screen_tc_control

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.FragmentTcBinding
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.service.BleServiceState
import com.astar.osterrig.controllightmvvm.utils.listeners.SeekBarChangeListener
import com.astar.osterrig.controllightmvvm.utils.setColorPreviewBackground
import com.astar.osterrig.controllightmvvm.utils.toPercentValue
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

internal class TcControlFragment : BaseFragment<TcControlViewModel>() {

    override val mModel: TcControlViewModel by viewModel()
    private lateinit var binding: FragmentTcBinding

    private var deviceModel: ArrayList<DeviceModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            deviceModel = it.getSerializable(DEVICE_LIST_ARGS) as ArrayList<DeviceModel>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTcBinding.inflate(inflater, container, false)
        initViews()
        addObservables()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        controlViewModel.connect(deviceModel)
    }

    private fun initViews() {
        binding.sbLightness.setOnSeekBarChangeListener(onSeekBarChangeListener)
        binding.sbTemperature.setOnSeekBarChangeListener(onSeekBarChangeListener)
        mModel.setTemperature(0)
    }

    private fun addObservables() {
        mModel.lightnessLiveData.observe(viewLifecycleOwner, {
            showPreviewLightness(it)
        })
        mModel.temperatureLiveData.observe(viewLifecycleOwner, {
            showPreviewTemperature(it)
        })
        mModel.temperaturePreviewLiveData.observe(viewLifecycleOwner, {
            showPreviewColor(it)
        })
    }

    override fun addObserversControl() {
        controlViewModel.connectionState.observe(viewLifecycleOwner, {
            when (it) {
                is BleServiceState.Battery -> showBatteryLevel(it.device, it.batteryValue)
            }
        })
    }

    override fun removeObserversControl() {
        controlViewModel.connectionState.removeObservers(viewLifecycleOwner)
    }

    private fun showBatteryLevel(device: BluetoothDevice, batteryValue: Int) {
        binding.topControlPanel.ivBatteryView.setBatteryLevel(batteryValue)
    }

    private fun showPreviewColor(color: Int) {
        binding.tvTemperaturePreview.setColorPreviewBackground(color)
    }

    private fun showPreviewLightness(lightness: Int) {
        binding.tvLabelLightness.toPercentValue(
            value = lightness,
            formattedText = getString(R.string.tv_lightness)
        )
    }

    @SuppressLint("SetTextI18n")
    private fun showPreviewTemperature(temperature: Triple<Int, Int, Int>) {
        binding.tvTemperaturePreview.text = "${temperature.first}K"
    }

    private val onSeekBarChangeListener = object : SeekBarChangeListener() {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            when (seekBar) {
                binding.sbLightness -> {
                    mModel.setLightness(progress)
                    if (progress % 5 == 0) controlViewModel.setLightness(deviceModel, progress)
                }
                binding.sbTemperature -> {
                    mModel.setTemperature(progress)
                    if (progress % 5 == 0) sendColor()
                }
            }
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            super.onStopTrackingTouch(seekBar)
            when (seekBar) {
                binding.sbLightness -> {
                    mModel.setLightness(seekBar.progress)
                    controlViewModel.setLightness(deviceModel, seekBar.progress)
                }
                binding.sbTemperature -> {
                    mModel.setTemperature(seekBar.progress)
                    sendColor()
                }
            }
        }
    }

    private fun sendColor() {
        val kalvin = mModel.temperatureLiveData.value
        kalvin?.let {
            controlViewModel.setColor(deviceModel, kalvin.second, kalvin.third)
            Log.d(TAG, "Отправка данных: ${deviceModel[0].macAddress}, ${kalvin.second} ${kalvin.third}")
        }
    }

    companion object {

        private const val TAG = "TcControlFragment"

        private const val DEVICE_LIST_ARGS = "device_list"

        @JvmStatic
        fun newInstance(deviceList: ArrayList<DeviceModel>) =
            TcControlFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(DEVICE_LIST_ARGS, deviceList)
                }
            }
    }
}