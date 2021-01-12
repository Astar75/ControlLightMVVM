package com.astar.osterrig.controllightmvvm.view.screen_rgb_control

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.annotation.ColorInt
import androidx.fragment.app.activityViewModels
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.FragmentRgbControlBinding
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.service.BleServiceState
import com.astar.osterrig.controllightmvvm.utils.*
import com.astar.osterrig.controllightmvvm.utils.listeners.SeekBarChangeListener
import com.astar.osterrig.controllightmvvm.view.MainActivityViewModel
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment
import com.skydoves.colorpickerview.listeners.ColorListener
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.math.abs

internal class RgbControlFragment : BaseFragment<RgbControlViewModel>() {

    override val mModel: RgbControlViewModel by viewModel()
    private val controlViewModel by activityViewModels<MainActivityViewModel>()

    private lateinit var binding: FragmentRgbControlBinding
    private lateinit var deviceModel: DeviceModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            deviceModel = it.getParcelable(DEVICE_MODEL_ARG)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRgbControlBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        connectToDevice(deviceModel)
        addObserver()
    }

    private fun connectToDevice(deviceModel: DeviceModel) {
        controlViewModel.connect(deviceModel)
    }

    private fun addObserver() {
        mModel.currentLightness.observe(this, {
            binding.tvLightness.toPercentValue(
                value = binding.sbLightness.progress,
                formattedText = getString(R.string.tv_lightness)
            )
        })
        mModel.currentColor.observe(this, {
            binding.colorPreview.setColorPreviewBackground(it)
            binding.tvColorPreview.text = colorIntToHexString(it)
        })
        mModel.colorPresetCellOne.observe(this, { setPresetColor(0, it) })
        mModel.colorPresetCellTwo.observe(this, { setPresetColor(1, it) })
        mModel.colorPresetCellThree.observe(this, { setPresetColor(2, it) })
        mModel.colorPresetCellFour.observe(this, { setPresetColor(3, it) })

        controlViewModel.connectionState.observe(viewLifecycleOwner, {
            connectionStateListener(it)
        })
    }

    private fun connectionStateListener(connectionService: BleServiceState) {
        when (connectionService) {
            is BleServiceState.Connecting -> {
                showToastMessage("Соединение с устройством")
            }
            is BleServiceState.Connected -> {
                showToastMessage("Успешное соединение")
            }
            is BleServiceState.Disconnected -> {
                showToastMessage("Устройство разъединено")
            }
            is BleServiceState.FailedToConnect -> {
                showToastMessage("Ошибка соедиения с устройством")
            }
        }
    }

    private fun setPresetColor(cell: Int, @ColorInt color: Int) {
        when (cell) {
            0 -> {
                binding.tvPresetColor1.text = colorIntToHexString(color)
                binding.viewPresetColor1.setBackgroundColor(color)
            }
            1 -> {
                binding.tvPresetColor2.text = colorIntToHexString(color)
                binding.viewPresetColor2.setBackgroundColor(color)
            }
            2 -> {
                binding.tvPresetColor3.text = colorIntToHexString(color)
                binding.viewPresetColor3.setBackgroundColor(color)
            }
            3 -> {
                binding.tvPresetColor4.text = colorIntToHexString(color)
                binding.viewPresetColor4.setBackgroundColor(color)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        with(binding) {
            with(topControlPanel) {
                ivDeviceList.setOnClickListener { navigateToDeviceList() }
                ivPower.setOnClickListener { showToastMessage("Включить выключить лампу") }
            }
            with(bottomNavPanel) {
                if (deviceModel.typeSaber == Constants.TypeSaber.RGB) {
                    btnOne.text = context?.getString(R.string.title_tab_rgb_control)
                    btnTwo.text = context?.getString(R.string.title_tab_ftp_control)
                    btnThree.text = context?.getString(R.string.title_tab_fnc_control)
                    btnFour.visibility = View.GONE

                    btnTwo.setOnClickListener { navigateToFtpForRgbControl(deviceModel) }
                    btnThree.setOnClickListener { navigateToFncRgbControl(deviceModel) }

                } else if (deviceModel.typeSaber == Constants.TypeSaber.WALS) {
                    btnOne.text = context?.getString(R.string.title_tab_rgb_control)
                    btnTwo.text = context?.getString(R.string.title_tab_cct_control)
                    btnThree.text = context?.getString(R.string.title_tab_ftp_control)
                    btnFour.text = context?.getString(R.string.title_tab_fnc_control)
                    btnFour.visibility = View.VISIBLE

                    btnTwo.setOnClickListener { navigateToCctControl(deviceModel) }
                    btnThree.setOnClickListener { navigateToFtpForWalsControl(deviceModel) }
                    btnFour.setOnClickListener { navigateToFncControl(deviceModel) }
                }

                btnOne.setBackgroundTint(R.color.button_active_color)

            }
            colorPicker.colorListener = colorListener
            colorPicker.setOnTouchListener(touchListener)
            sbLightness.setOnSeekBarChangeListener(changeListener)
            viewPresetColor1.setOnClickListener(clickListener)
            viewPresetColor2.setOnClickListener(clickListener)
            viewPresetColor3.setOnClickListener(clickListener)
            viewPresetColor4.setOnClickListener(clickListener)
            viewPresetColor1.setOnLongClickListener(longClickListener)
            viewPresetColor2.setOnLongClickListener(longClickListener)
            viewPresetColor3.setOnLongClickListener(longClickListener)
            viewPresetColor4.setOnLongClickListener(longClickListener)
        }
    }

    private val colorListener =
        ColorListener { color, fromUser ->
            if (fromUser) {
                if (abs(color) % 5 == 0)
                    controlViewModel.setColor(deviceModel, color)
            }
            mModel.setColor(color)
        }

    @SuppressLint("ClickableViewAccessibility")
    private val touchListener = View.OnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            controlViewModel.setColor(deviceModel, binding.colorPicker.color)
            true
        } else
        false
    }

    private val clickListener = View.OnClickListener { view ->
        when (view) {
            binding.viewPresetColor1 -> { mModel.colorPresetCellOne.value?.let { binding.colorPicker.setInitialColor(it) } }
            binding.viewPresetColor2 -> { mModel.colorPresetCellTwo.value?.let { binding.colorPicker.setInitialColor(it) } }
            binding.viewPresetColor3 -> { mModel.colorPresetCellThree.value?.let { binding.colorPicker.setInitialColor(it) } }
            binding.viewPresetColor4 -> { mModel.colorPresetCellFour.value?.let { binding.colorPicker.setInitialColor(it) } }
        }
    }

    private val longClickListener = View.OnLongClickListener { v ->
        val color = binding.colorPicker.color
        when (v) {
            binding.viewPresetColor1 -> { mModel.setColorPresetCell(0, color) }
            binding.viewPresetColor2 -> { mModel.setColorPresetCell(1, color) }
            binding.viewPresetColor3 -> { mModel.setColorPresetCell(2, color) }
            binding.viewPresetColor4 -> { mModel.setColorPresetCell(3, color) }
        }
        false
    }

    private val changeListener = object : SeekBarChangeListener() {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            mModel.setLightness(progress)
            if (progress % 5 == 0)
                controlViewModel.setLightness(deviceModel, progress)
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            controlViewModel.setLightness(deviceModel, seekBar.progress)
        }
    }

    companion object {
        private const val DEVICE_MODEL_ARG = "device_model"

        @JvmStatic
        fun newInstance(deviceModel: DeviceModel) =
            RgbControlFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DEVICE_MODEL_ARG, deviceModel)
                }
            }
    }
}