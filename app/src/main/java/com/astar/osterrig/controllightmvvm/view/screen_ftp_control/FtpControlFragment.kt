package com.astar.osterrig.controllightmvvm.view.screen_ftp_control

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.FragmentFtpControlBinding
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.service.BleServiceState
import com.astar.osterrig.controllightmvvm.utils.colorIntToHexString
import com.astar.osterrig.controllightmvvm.utils.listeners.SeekBarChangeListener
import com.astar.osterrig.controllightmvvm.utils.setBackgroundTint
import com.astar.osterrig.controllightmvvm.utils.setColorPreviewBackground
import com.astar.osterrig.controllightmvvm.utils.toPercentValue
import com.astar.osterrig.controllightmvvm.view.MainActivityViewModel
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

internal class FtpControlFragment : BaseFragment<FtpControlViewModel>() {

    private lateinit var binding: FragmentFtpControlBinding

    override val mModel: FtpControlViewModel by viewModel()
    private lateinit var deviceModel: ArrayList<DeviceModel>

    private var selectedButton: SelectButton = SelectButton.BUTTON_1
    private var selectedGradientMode: SelectButton = SelectButton.BUTTON_1

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
        binding = FragmentFtpControlBinding.inflate(layoutInflater, container, false)
        initView()
        addObservers()
        return binding.root
    }

    private fun initView() {
        with(binding.bottomNavPanel) {
            btnOne.text = context?.getString(R.string.title_tab_rgb_control)
            btnTwo.text = context?.getString(R.string.title_tab_cct_control)
            btnThree.text = context?.getString(R.string.title_tab_ftp_control)
            btnFour.text = context?.getString(R.string.title_tab_fnc_control)

            btnThree.setBackgroundTint(R.color.button_active_color)
            btnOne.setOnClickListener { navigateToRgbControl(deviceModel, true) }
            btnTwo.setOnClickListener { navigateToCctControl(deviceModel) }
            btnFour.setOnClickListener { navigateToFncControl(deviceModel) }
        }

        binding.sbRedColor.setOnSeekBarChangeListener(rgbChangeListener)
        binding.sbGreenColor.setOnSeekBarChangeListener(rgbChangeListener)
        binding.sbBlueColor.setOnSeekBarChangeListener(rgbChangeListener)
        binding.sbSaturationColor.setOnSeekBarChangeListener(hsvChangeListener)
        binding.sbLightnessColor.setOnSeekBarChangeListener(hsvChangeListener)

        binding.viewButton1.setOnClickListener(clickListener)
        binding.viewButton2.setOnClickListener(clickListener)
        binding.viewButton3.setOnClickListener(clickListener)
        binding.viewButton4.setOnClickListener(clickListener)

        binding.tvSharp.setOnClickListener(clickListener)
        binding.tvSmooth.setOnClickListener(clickListener)

        binding.viewButton2.setOnLongClickListener(longClickListener)
        binding.viewButton3.setOnLongClickListener(longClickListener)
        binding.viewButton4.setOnLongClickListener(longClickListener)

        updateButtons()
    }

    private fun addObservers() {
        mModel.currentColor.observe(viewLifecycleOwner, {
            binding.tvColorPreview.setColorPreviewBackground(it)
            binding.tvColorPreview.text = colorIntToHexString(it)
        })
        mModel.colorOne.observe(viewLifecycleOwner, { binding.viewButton1.setBackgroundColor(it) })
        mModel.colorTwo.observe(viewLifecycleOwner, { binding.viewButton2.setBackgroundColor(it) })
        mModel.colorThree.observe(viewLifecycleOwner, { binding.viewButton3.setBackgroundColor(it) })
        mModel.colorFour.observe(viewLifecycleOwner, { binding.viewButton4.setBackgroundColor(it) })
        mModel.redColor.observe(viewLifecycleOwner, { binding.tvRedPreview.text = it.toString() })
        mModel.greenColor.observe(viewLifecycleOwner, { binding.tvGreenPreview.text = it.toString() })
        mModel.blueColor.observe(viewLifecycleOwner, { binding.tvBluePreview.text = it.toString() })
        mModel.saturationColor.observe(viewLifecycleOwner, { binding.tvSaturationPreview.text = it.toString() })
        mModel.lightness.observe(viewLifecycleOwner, { binding.tvLightnessPreview.toPercentValue(it, 100, null) })
    }

    override fun addObserversControl() {
        controlViewModel.connectionState.observe(viewLifecycleOwner,  {
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

    private val rgbChangeListener = object : SeekBarChangeListener() {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            when (seekBar) {
                binding.sbRedColor -> {
                    mModel.setRed(progress)
                }
                binding.sbGreenColor -> {
                    mModel.setGreen(progress)
                }
                binding.sbBlueColor -> {
                    mModel.setBlue(progress)
                }
            }
        }
        override fun onStopTrackingTouch(seekBar: SeekBar) {
            when (seekBar) {
                binding.sbRedColor -> {
                    mModel.setRed(seekBar.progress)
                }
                binding.sbGreenColor -> {
                    mModel.setGreen(seekBar.progress)
                }
                binding.sbBlueColor -> {
                    mModel.setBlue(seekBar.progress)
                }
            }
        }
    }

    private val hsvChangeListener = object : SeekBarChangeListener() {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            when (seekBar) {
                binding.sbSaturationColor -> {
                    mModel.setSaturation(progress)
                }
                binding.sbLightnessColor -> {
                    mModel.setLightness(progress)
                }
            }
        }
        override fun onStopTrackingTouch(seekBar: SeekBar) {
            when (seekBar) {
                binding.sbSaturationColor -> {
                    mModel.setSaturation(seekBar.progress)
                }
                binding.sbLightnessColor -> {
                    mModel.setLightness(seekBar.progress)
                }
            }
        }
    }

    private val clickListener = View.OnClickListener { v ->
        when (v) {
            binding.viewButton1 -> {
                selectedButton = SelectButton.BUTTON_1
                mModel.setCurrentColorCell(0)
            }
            binding.viewButton2 -> {
                selectedButton = SelectButton.BUTTON_2
                mModel.setCurrentColorCell(1)
            }
            binding.viewButton3 -> {
                selectedButton = SelectButton.BUTTON_3
                mModel.setCurrentColorCell(2)
            }
            binding.viewButton4 -> {
                selectedButton = SelectButton.BUTTON_4
                mModel.setCurrentColorCell(3)
            }
            binding.tvSmooth -> {
                selectedGradientMode = SelectButton.SMOOTH
            }
            binding.tvSharp -> {
                selectedGradientMode = SelectButton.SHARP
            }
        }
        updateButtons()
    }

    private val longClickListener = View.OnLongClickListener { v ->
        when (v) {
            binding.viewButton2 -> {

            }
            binding.viewButton3 -> {

            }
            binding.viewButton4 -> {

            }
        }
        updateButtons()
        true
    }

    private fun updateButtons() {
        binding.viewButtonSelector1.visibility =
            if (selectedButton == SelectButton.BUTTON_1) View.VISIBLE else View.INVISIBLE
        binding.viewButtonSelector2.visibility =
            if (selectedButton == SelectButton.BUTTON_2) View.VISIBLE else View.INVISIBLE
        binding.viewButtonSelector3.visibility =
            if (selectedButton == SelectButton.BUTTON_3) View.VISIBLE else View.INVISIBLE
        binding.viewButtonSelector4.visibility =
            if (selectedButton == SelectButton.BUTTON_4) View.VISIBLE else View.INVISIBLE

        binding.tvSmooth.text = if (selectedGradientMode == SelectButton.SMOOTH)
            Html.fromHtml("<u>${getString(R.string.tv_smooth)}</u>")
        else
            getString(R.string.tv_smooth)

        binding.tvSharp.text = if (selectedGradientMode == SelectButton.SHARP)
            Html.fromHtml("<u>${getString(R.string.tv_sharp)}</u>")
        else
            getString(R.string.tv_sharp)

    }

    companion object {
        private const val DEVICE_MODEL_ARG = "device_model"

        @JvmStatic
        fun newInstance(deviceModel: ArrayList<DeviceModel>) =
            FtpControlFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(DEVICE_MODEL_ARG, deviceModel)
                }
            }
    }

    private enum class SelectButton {
        BUTTON_1, BUTTON_2, BUTTON_3, BUTTON_4, SMOOTH, SHARP
    }
}