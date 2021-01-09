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
import com.astar.osterrig.controllightmvvm.utils.DataProvider
import com.astar.osterrig.controllightmvvm.utils.setBackgroundTint
import com.astar.osterrig.controllightmvvm.utils.toPercentValue
import com.astar.osterrig.controllightmvvm.view.adapters.CctColorListAdapter
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

internal class CctControlFragment : BaseFragment<CctControlViewModel>() {

    override val mModel: CctControlViewModel by viewModel()

    private lateinit var adapter: CctColorListAdapter
    private lateinit var binding: FragmentCctControlBinding

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
        binding = FragmentCctControlBinding.inflate(inflater, container, false)
        initView()
        addObservers()
        return binding.root
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

    private val cctColorListAdapterItemClickListener =
        object : CctColorListAdapter.OnItemClickListener {
            override fun onItemClickListener(item: CctColorEntity) {
                mModel.setColor(deviceModel, item)
                setColor(deviceModel, item)
            }
        }

    private val changeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            if (seekBar == binding.sbLightness) {
                mModel.setLightness(progress)
            } else if (seekBar == binding.sbTint) {
                mModel.setTint(progress)
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            if (seekBar == binding.sbLightness) {
                mModel.setLightness(seekBar.progress)
                setLightness(deviceModel, seekBar.progress)
            } else if (seekBar == binding.sbTint) {
                mModel.setTint(seekBar.progress)
            }
        }
    }

    companion object {
        private const val DEVICE_MODEL_ARG = "device_model"

        @JvmStatic
        fun newInstance(deviceModel: DeviceModel) =
            CctControlFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DEVICE_MODEL_ARG, deviceModel)
                }
            }
    }
}