package com.astar.osterrig.controllightmvvm.view.screen_cct_control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.FragmentCctControlBinding
import com.astar.osterrig.controllightmvvm.model.data.CctColorEntity
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.service.BleServiceState
import com.astar.osterrig.controllightmvvm.utils.DataProvider
import com.astar.osterrig.controllightmvvm.utils.listeners.SeekBarChangeListener
import com.astar.osterrig.controllightmvvm.utils.setBackgroundTint
import com.astar.osterrig.controllightmvvm.utils.toPercentValue
import com.astar.osterrig.controllightmvvm.view.adapters.CctColorListAdapter
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

internal class CctControlFragment : BaseFragment<CctControlViewModel>() {

    override val mModel: CctControlViewModel by viewModel()

    private lateinit var adapter: CctColorListAdapter
    private lateinit var binding: FragmentCctControlBinding
    private lateinit var deviceModel: List<DeviceModel>

    private var currentCctColor: CctColorEntity = CctColorEntity("0", 0, 0, 0, 0, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            deviceModel = it.getSerializable(DEVICE_MODEL_ARG) as ArrayList<DeviceModel>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCctControlBinding.inflate(inflater, container, false)
        initView()
        addObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controlViewModel.setLightness(deviceModel, 255)
    }

    private fun initView() {
        adapter = CctColorListAdapter()
        adapter.addData(DataProvider.getCctColorList())
        adapter.setOnItemClickListener(cctColorListAdapterItemClickListener)

        with(binding) {
            with(bottomNavPanel) {
                btnOne.text = context?.getString(R.string.title_tab_rgb_control)
                btnTwo.text = context?.getString(R.string.title_tab_cct_control)
                btnThree.text = context?.getString(R.string.title_tab_ftp_control)
                btnFour.text = context?.getString(R.string.title_tab_fnc_control)

                btnTwo.setBackgroundTint(R.color.button_active_color)
                btnOne.setOnClickListener { navigateToRgbControl(deviceModel, true) }
                btnThree.setOnClickListener { navigateToFtpForWalsControl(deviceModel) }
                // btnFour.setOnClickListener { navigateToFncControl(deviceModel) }
                btnFour.setOnClickListener { navigateToFncRgbControl(deviceModel) }
            }
            recyclerViewCctColorList.addItemDecoration(
                DividerItemDecoration(
                    context,
                    RecyclerView.VERTICAL
                )
            )
            recyclerViewCctColorList.adapter = adapter
            sbLightness.setOnSeekBarChangeListener(changeListener)
            sbTint.progress = 50
            sbTint.setOnSeekBarChangeListener(changeListener)
        }
    }

    private fun addObservers() {
        mModel.tintPreview.observe(viewLifecycleOwner, {
            binding.tvTint.text = String.format(getString(R.string.tv_tint), it)
        })
        mModel.lightness.observe(viewLifecycleOwner, {
            binding.tvLightness.toPercentValue(
                value = it,
                formattedText = getString(R.string.tv_lightness)
            )
        })
    }

    override fun addObserversControl() {
        controlViewModel.connectionState.observe(viewLifecycleOwner, {
            connectionStateListener(it)
        })
    }

    override fun removeObserversControl() {
        controlViewModel.connectionState.removeObservers(viewLifecycleOwner)
    }

    private fun connectionStateListener(serviceState: BleServiceState) {
        when(serviceState) {
            is BleServiceState.Battery -> {
                showBatteryLevel(serviceState.batteryValue)
            }
        }
    }

    private fun showBatteryLevel(batteryValue: Int) {
        binding.topControlPanel.ivBatteryView.setBatteryLevel(batteryValue)
    }

    private val cctColorListAdapterItemClickListener =
        object : CctColorListAdapter.OnItemClickListener {
            override fun onItemClickListener(cctColor: CctColorEntity) {
                currentCctColor = cctColor
                calculateLightnessColor(cctColor)
            }
        }

    private val changeListener = object : SeekBarChangeListener() {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            if (seekBar == binding.sbLightness) {
                mModel.setLightness(progress)
                if (progress % 5 == 0) {
                    calculateLightnessColor(currentCctColor)
                }
            } else if (seekBar == binding.sbTint) {
                mModel.setTint(progress)
                controlViewModel.cctColor.value?.let { controlViewModel.setTint(deviceModel, progress, it) }
            }
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            if (seekBar == binding.sbLightness) {
                mModel.setLightness(seekBar.progress)
                calculateLightnessColor(currentCctColor)
            } else if (seekBar == binding.sbTint) {
                mModel.setTint(seekBar.progress)
            }
        }
    }

    private fun calculateLightnessColor(cctColorEntity: CctColorEntity) {
        val coefficientLightness = (binding.sbLightness.progress.toFloat() / 255f).toFloat()
        var red: Float = cctColorEntity.red.toFloat()
        var green: Float = cctColorEntity.green.toFloat()
        var blue: Float = cctColorEntity.blue.toFloat()
        var white: Float = cctColorEntity.white.toFloat()

        red *= coefficientLightness
        green *= coefficientLightness
        blue *= coefficientLightness
        white *= coefficientLightness

        val newCctColor = CctColorEntity(
            cctColorEntity.kelvin,
            cctColorEntity.backgroundCell,
            red.toInt(), green.toInt(), blue.toInt(), white.toInt()
        )

        controlViewModel.setCctColor(deviceModel, newCctColor)
    }

    companion object {
        private const val DEVICE_MODEL_ARG = "device_model"

        @JvmStatic
        fun newInstance(deviceModel: ArrayList<DeviceModel>) =
            CctControlFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(DEVICE_MODEL_ARG, deviceModel)
                }
            }
    }
}