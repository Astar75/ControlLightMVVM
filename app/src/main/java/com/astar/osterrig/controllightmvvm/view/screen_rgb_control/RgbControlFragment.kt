package com.astar.osterrig.controllightmvvm.view.screen_rgb_control

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.lifecycle.ViewModel
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.FragmentRgbControlBinding
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment
import com.skydoves.colorpickerview.listeners.ColorListener
import org.koin.android.viewmodel.ext.android.viewModel

internal class RgbControlFragment : BaseFragment() {

    override val mModel: ViewModel by viewModel<RgbControlViewModel>()

    private lateinit var mBinding: FragmentRgbControlBinding
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
        mBinding = FragmentRgbControlBinding.inflate(layoutInflater)
        initView()
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        connect(deviceModel)
    }

    private fun initView() {
        mBinding.topControlPanel.ivDeviceList.setOnClickListener { navigateToDeviceList() }
        mBinding.topControlPanel.ivPower.setOnClickListener {
            showToastMessage("Включить выключить лампу")
        }  // включить выключить лампу
        mBinding.colorPicker.colorListener = colorListener
        mBinding.sbLightness.setOnSeekBarChangeListener(changeListener)
        with(mBinding.bottomNavPanel) {
            btnOne.text = context?.getString(R.string.title_tab_rgb_control)
            btnTwo.text = context?.getString(R.string.title_tab_cct_control)
            btnThree.text = context?.getString(R.string.title_tab_ftp_control)
            btnFour.text = context?.getString(R.string.title_tab_fnc_control)

            btnTwo.setOnClickListener { navigateToCctControl(deviceModel) }
            btnThree.setOnClickListener { navigateToFtpControl(deviceModel) }
            btnFour.setOnClickListener { navigateToFncControl(deviceModel) }
        }
    }

    private val colorListener =
        ColorListener { color, _ ->
            val colorStr = String.format("r%dg%db%d", Color.red(color), Color.green(color), Color.blue(color))
            setColor(deviceModel, colorStr)
        }

    private val changeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            val lightness = seekBar.progress
            setLightness(deviceModel, lightness)
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