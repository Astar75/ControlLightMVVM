package com.astar.osterrig.controllightmvvm.view.screen_ftp_rgb_control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.FragmentFtpRgbControlBinding
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.utils.Constants
import com.astar.osterrig.controllightmvvm.utils.listeners.SeekBarChangeListener
import com.astar.osterrig.controllightmvvm.utils.setBackgroundTint
import com.astar.osterrig.controllightmvvm.utils.setColorPreviewBackground
import com.astar.osterrig.controllightmvvm.view.MainActivityViewModel
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment
import org.koin.android.ext.android.bind
import org.koin.android.viewmodel.ext.android.viewModel

internal class FtpRgbControlFragment : BaseFragment<FtpRgbControlViewModel>() {

    override val mModel: FtpRgbControlViewModel by viewModel()
    private val controlViewModel: MainActivityViewModel by activityViewModels()

    private lateinit var binding: FragmentFtpRgbControlBinding
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
        binding = FragmentFtpRgbControlBinding.inflate(inflater, container, false)
        initViews()
        addObservers()
        return binding.root
    }

    private fun initViews() {
        with(binding.bottomNavPanel) {

            if (deviceModel.typeSaber == Constants.TypeSaber.RGB) {
                btnOne.text = context?.getString(R.string.title_tab_rgb_control)
                btnTwo.text = context?.getString(R.string.title_tab_ftp_control)
                btnThree.text = context?.getString(R.string.title_tab_fnc_control)
                btnFour.visibility = View.GONE

                btnOne.setOnClickListener { navigateToRgbControl(deviceModel, false) }
                btnThree.setOnClickListener { navigateToFncRgbControl(deviceModel) }
            }

            btnTwo.setBackgroundTint(R.color.button_active_color)
        }

        binding.sbRedColor.setOnSeekBarChangeListener(changeListener)
        binding.sbGreenColor.setOnSeekBarChangeListener(changeListener)
        binding.sbBlueColor.setOnSeekBarChangeListener(changeListener)
    }

    private fun addObservers() {
        mModel.color.observe(
            viewLifecycleOwner,
            { binding.colorPreview.setColorPreviewBackground(it) })
        mModel.textColor.observe(viewLifecycleOwner, { binding.tvColorPreviewIndicator.text = it })
        mModel.red.observe(viewLifecycleOwner, { binding.tvPreviewRed.text = it.toString() })
        mModel.green.observe(viewLifecycleOwner, { binding.tvPreviewGreen.text = it.toString() })
        mModel.blue.observe(viewLifecycleOwner, { binding.tvPreviewBlue.text = it.toString() })
    }

    private val changeListener = object : SeekBarChangeListener() {
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
            if (progress % 5 == 0) controlViewModel.setColor(
                deviceModel,
                binding.sbRedColor.progress,
                binding.sbGreenColor.progress,
                binding.sbBlueColor.progress
            )
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
            controlViewModel.setColor(
                deviceModel,
                binding.sbRedColor.progress,
                binding.sbGreenColor.progress,
                binding.sbBlueColor.progress
            )
        }
    }

    companion object {
        private const val DEVICE_MODEL_ARG = "device_model"

        @JvmStatic
        fun newInstance(deviceModel: DeviceModel) =
            FtpRgbControlFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DEVICE_MODEL_ARG, deviceModel)
                }
            }
    }
}