package com.astar.osterrig.controllightmvvm.view.screen_cct_control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.FragmentCctControlBinding
import com.astar.osterrig.controllightmvvm.model.data.CctColorEntity
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.utils.DataProvider
import com.astar.osterrig.controllightmvvm.view.adapters.CctColorListAdapter
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment
import org.koin.android.ext.android.bind
import org.koin.android.viewmodel.ext.android.viewModel

internal class CctControlFragment : BaseFragment() {

    override val mModel: ViewModel by viewModel<CctControlViewModel>()

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
        return binding.root
    }

    private fun initView() {
        adapter = CctColorListAdapter()
        adapter.addData(DataProvider.getCctColorList())
        adapter.setOnItemClickListener(cctColorListAdapterItemClickListener)

        with(binding.bottomNavPanel) {
            btnOne.text = context?.getString(R.string.title_tab_rgb_control)
            btnTwo.text = context?.getString(R.string.title_tab_cct_control)
            btnThree.text = context?.getString(R.string.title_tab_ftp_control)
            btnFour.text = context?.getString(R.string.title_tab_fnc_control)

            btnOne.setOnClickListener { navigateToRgbControl(deviceModel, true) }
            btnThree.setOnClickListener { navigateToFtpControl(deviceModel) }
            btnFour.setOnClickListener { navigateToFncControl(deviceModel) }
        }

        binding.recyclerViewCctColorList.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        binding.recyclerViewCctColorList.adapter = adapter
        binding.sbLightness.setOnSeekBarChangeListener(changeListener)
        binding.sbTint.setOnSeekBarChangeListener(changeListener)
    }

    private val cctColorListAdapterItemClickListener = object : CctColorListAdapter.OnItemClickListener {
        override fun onItemClickListener(item: CctColorEntity) {
            val colorString = String.format("r%dg%db%dw%d", item.red, item.green, item.blue, item.white)
            setColor(deviceModel, colorString)
        }
    }

    private val changeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            if (seekBar == binding.sbLightness) {
                setLightness(deviceModel, seekBar.progress)
            } else if (seekBar == binding.sbTint) {

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