package com.astar.osterrig.controllightmvvm.view.screen_fnc_rgb_control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.FragmentFncRgbControlBinding
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.model.data.FunctionRgb
import com.astar.osterrig.controllightmvvm.utils.Constants
import com.astar.osterrig.controllightmvvm.utils.DataProvider
import com.astar.osterrig.controllightmvvm.utils.listeners.SeekBarChangeListener
import com.astar.osterrig.controllightmvvm.utils.setBackgroundTint
import com.astar.osterrig.controllightmvvm.utils.toPercentValue
import com.astar.osterrig.controllightmvvm.view.MainActivityViewModel
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment
import com.astar.osterrig.controllightmvvm.view.dialogs.SelectFunctionDialog
import com.astar.osterrig.controllightmvvm.view.screen_fnc_wals_control.FncWalsControlFragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.include_bottom_nav_panel.*
import org.koin.android.viewmodel.ext.android.viewModel

internal class FncRgbControlFragment : BaseFragment<FncRgbControlViewModel>() {

    override val mModel: FncRgbControlViewModel by viewModel()

    private val controlViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var binding: FragmentFncRgbControlBinding

    private var selectedFunctionCell: FunctionCell = FunctionCell.FUNCTION_1
    private lateinit var deviceModel: DeviceModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            deviceModel = it.getParcelable(FncWalsControlFragment.DEVICE_MODEL_ARG)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFncRgbControlBinding.inflate(inflater, container,false)
        initView()
        addObservers()
        return binding.root
    }

    private fun initView() {
        with(binding.bottomNavPanel) {

            if (deviceModel.typeSaber == Constants.TypeSaber.RGB) {
                btnOne.text = context?.getString(R.string.title_tab_rgb_control)
                btnTwo.text = context?.getString(R.string.title_tab_ftp_control)
                btnThree.text = context?.getString(R.string.title_tab_fnc_control)
                btnFour.visibility = View.GONE

                btnOne.setOnClickListener { navigateToRgbControl(deviceModel, false) }
                btnTwo.setOnClickListener { navigateToFtpForRgbControl(deviceModel) }
            } else if (deviceModel.typeSaber == Constants.TypeSaber.WALS) {
                btnOne.text = context?.getString(R.string.title_tab_rgb_control)
                btnTwo.text = context?.getString(R.string.title_tab_cct_control)
                btnThree.text = context?.getString(R.string.title_tab_ftp_control)
                btnFour.text = context?.getString(R.string.title_tab_fnc_control)
                btnFour.visibility = View.VISIBLE

                btnOne.setOnClickListener { navigateToRgbControl(deviceModel, true) }
                btnTwo.setOnClickListener { navigateToCctControl(deviceModel) }
                btnThree.setOnClickListener { navigateToFtpForWalsControl(deviceModel) }
            }

            btnThree.setBackgroundTint(R.color.button_active_color)
        }

        binding.btnFunctionOne.setOnClickListener(selectClickListener)
        binding.btnFunctionTwo.setOnClickListener(selectClickListener)
        binding.btnFunctionThree.setOnClickListener(selectClickListener)
        binding.btnFunctionFour.setOnClickListener(selectClickListener)
        binding.btnFunctionFive.setOnClickListener(selectClickListener)
        binding.btnFunctionSix.setOnClickListener(selectClickListener)
        binding.btnFunctionSeven.setOnClickListener(selectClickListener)
        binding.btnFunctionEight.setOnClickListener(selectClickListener)
        binding.btnFunctionNine.setOnClickListener(selectClickListener)

        binding.btnFunctionOne.setOnLongClickListener(longClickListener)
        binding.btnFunctionTwo.setOnLongClickListener(longClickListener)
        binding.btnFunctionThree.setOnLongClickListener(longClickListener)
        binding.btnFunctionFour.setOnLongClickListener(longClickListener)
        binding.btnFunctionFive.setOnLongClickListener(longClickListener)
        binding.btnFunctionSix.setOnLongClickListener(longClickListener)
        binding.btnFunctionSeven.setOnLongClickListener(longClickListener)
        binding.btnFunctionEight.setOnLongClickListener(longClickListener)
        binding.btnFunctionNine.setOnLongClickListener(longClickListener)

        binding.sbLightness.setOnSeekBarChangeListener(seekBarChangeListener)
        binding.sbSpeed.setOnSeekBarChangeListener(seekBarChangeListener)

        updateFunctionCells()
    }

    private val selectClickListener = View.OnClickListener { v ->
        when (v) {
            binding.btnFunctionOne -> { selectedFunctionCell = FunctionCell.FUNCTION_1 }
            binding.btnFunctionTwo -> { selectedFunctionCell = FunctionCell.FUNCTION_2 }
            binding.btnFunctionThree -> { selectedFunctionCell = FunctionCell.FUNCTION_3 }
            binding.btnFunctionFour -> { selectedFunctionCell = FunctionCell.FUNCTION_4 }
            binding.btnFunctionFive -> { selectedFunctionCell = FunctionCell.FUNCTION_5 }
            binding.btnFunctionSix -> { selectedFunctionCell = FunctionCell.FUNCTION_6 }
            binding.btnFunctionSeven -> { selectedFunctionCell = FunctionCell.FUNCTION_7 }
            binding.btnFunctionEight -> { selectedFunctionCell = FunctionCell.FUNCTION_8 }
            binding.btnFunctionNine -> { selectedFunctionCell = FunctionCell.FUNCTION_9 }
        }
        mModel.selectFunction(selectedFunctionCell.ordinal)
        updateFunctionCells()
    }

    private val longClickListener = View.OnLongClickListener {
        openDialogSelectFunction(it.id)
        true
    }

    private fun openDialogSelectFunction(viewId: Int) {
        val functionList = DataProvider.getRgbFunctionNameList()
        val dialog = SelectFunctionDialog.newInstance(viewId, functionList)
        dialog.addCallback(selectFunctionListener)
        dialog.show(childFragmentManager, "select_function_dialog")
    }

    private val selectFunctionListener = object : SelectFunctionDialog.OnClickListener {
        override fun onClick(item: Triple<Int, String, Int>, viewId: Int) {
            when(viewId) {
                binding.btnFunctionOne.id -> { mModel.setFunctionToCell(0, item.first, item.second, item.third) }
                binding.btnFunctionTwo.id -> { mModel.setFunctionToCell(1, item.first, item.second, item.third) }
                binding.btnFunctionThree.id -> { mModel.setFunctionToCell(2, item.first, item.second, item.third) }
                binding.btnFunctionFour.id -> { mModel.setFunctionToCell(3, item.first, item.second, item.third) }
                binding.btnFunctionFive.id -> { mModel.setFunctionToCell(4, item.first, item.second, item.third) }
                binding.btnFunctionSix.id -> { mModel.setFunctionToCell(5, item.first, item.second, item.third) }
                binding.btnFunctionSeven.id -> { mModel.setFunctionToCell(6, item.first, item.second, item.third) }
                binding.btnFunctionEight.id -> { mModel.setFunctionToCell(7, item.first, item.second, item.third) }
                binding.btnFunctionNine.id -> { mModel.setFunctionToCell(8, item.first, item.second, item.third) }
            }
        }
    }

    private val seekBarChangeListener: SeekBarChangeListener = object : SeekBarChangeListener() {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            if (seekBar == binding.sbLightness) {
                mModel.setLightness(progress)
                if (progress % 5 == 0) controlViewModel.setLightness(deviceModel, progress)
            } else if (seekBar == binding.sbSpeed) {
                mModel.setSpeed(progress)
                if (progress % 5 == 0) controlViewModel.setSpeed(deviceModel, progress)
            }
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            if (seekBar == binding.sbLightness) {
                mModel.setLightnessCell(selectedFunctionCell.ordinal, seekBar.progress)
                controlViewModel.setLightness(deviceModel, seekBar.progress)
            } else if (seekBar == binding.sbSpeed) {
                mModel.setSpeedCell(selectedFunctionCell.ordinal, seekBar.progress)
                controlViewModel.setSpeed(deviceModel, seekBar.progress)
            }
        }
    }

    private fun addObservers() {
        mModel.cellFunctionOne.observe(viewLifecycleOwner, { updateFunctionIcon(it.icon, binding.btnFunctionOne) })
        mModel.cellFunctionTwo.observe(viewLifecycleOwner, { updateFunctionIcon(it.icon, binding.btnFunctionTwo) })
        mModel.cellFunctionThree.observe(viewLifecycleOwner, { updateFunctionIcon(it.icon, binding.btnFunctionThree) })
        mModel.cellFunctionFour.observe(viewLifecycleOwner, { updateFunctionIcon(it.icon, binding.btnFunctionFour) })
        mModel.cellFunctionFive.observe(viewLifecycleOwner, { updateFunctionIcon(it.icon, binding.btnFunctionFive) })
        mModel.cellFunctionSix.observe(viewLifecycleOwner, { updateFunctionIcon(it.icon, binding.btnFunctionSix) })
        mModel.cellFunctionSeven.observe(viewLifecycleOwner, { updateFunctionIcon(it.icon, binding.btnFunctionSeven) })
        mModel.cellFunctionEight.observe(viewLifecycleOwner, { updateFunctionIcon(it.icon, binding.btnFunctionEight) })
        mModel.cellFunctionNine.observe(viewLifecycleOwner, { updateFunctionIcon(it.icon, binding.btnFunctionNine) })

        mModel.selectedFunction.observe(viewLifecycleOwner, {
            updateFunctionControlUi(it)
            updateFunctionCells()
            updateFunctionView(it)
            sendFunction(it)
        })

        mModel.lightnessPreview.observe(viewLifecycleOwner, { binding.tvLightnessIndicator.toPercentValue(it, 255) })
        mModel.speedPreview.observe(viewLifecycleOwner, { binding.tvSpeedIndicator.toPercentValue(it, 2500) })

        mModel.functionPreview.observe(viewLifecycleOwner, {
            showFunctionPreview(it)
        })
    }

    private fun sendFunction(codeCommand: FunctionRgb) {
        controlViewModel.setLightness(deviceModel, codeCommand.lightness)
        controlViewModel.setSpeed(deviceModel, codeCommand.speed)
        controlViewModel.setFunction(deviceModel, Constants.TypeSaber.RGB, codeCommand.code.toString())
    }

    private fun showFunctionPreview(functionView: Pair<Boolean, IntArray>) {
        binding.gradientView.setSmooth(functionView.first)
        binding.gradientView.setColors(functionView.second.toList())
    }

    private fun updateFunctionIcon(iconDrawable: Int?, cellImage: ImageView) {
        iconDrawable?.let { id ->
            Glide.with(this).load(id).into(cellImage)
        }
    }

    private fun updateFunctionView(functionRgb: FunctionRgb) {

    }

    private fun updateFunctionControlUi(functionRgb: FunctionRgb) {
        binding.sbLightness.progress = functionRgb.lightness
        binding.sbSpeed.progress = functionRgb.speed
    }

    private fun updateFunctionCells() {
        binding.btnFunctionSelectorOne.visibility =
            if (selectedFunctionCell == FunctionCell.FUNCTION_1) View.VISIBLE else View.INVISIBLE
        binding.btnFunctionSelectorTwo.visibility =
            if (selectedFunctionCell == FunctionCell.FUNCTION_2) View.VISIBLE else View.INVISIBLE
        binding.btnFunctionSelectorThree.visibility =
            if (selectedFunctionCell == FunctionCell.FUNCTION_3) View.VISIBLE else View.INVISIBLE
        binding.btnFunctionSelectorFour.visibility =
            if (selectedFunctionCell == FunctionCell.FUNCTION_4) View.VISIBLE else View.INVISIBLE
        binding.btnFunctionSelectorFive.visibility =
            if (selectedFunctionCell == FunctionCell.FUNCTION_5) View.VISIBLE else View.INVISIBLE
        binding.btnFunctionSelectorSix.visibility =
            if (selectedFunctionCell == FunctionCell.FUNCTION_6) View.VISIBLE else View.INVISIBLE
        binding.btnFunctionSelectorSeven.visibility =
            if (selectedFunctionCell == FunctionCell.FUNCTION_7) View.VISIBLE else View.INVISIBLE
        binding.btnFunctionSelectorEight.visibility =
            if (selectedFunctionCell == FunctionCell.FUNCTION_8) View.VISIBLE else View.INVISIBLE
        binding.btnFunctionSelectorNine.visibility =
            if (selectedFunctionCell == FunctionCell.FUNCTION_9) View.VISIBLE else View.INVISIBLE
    }

    private enum class FunctionCell {
        FUNCTION_1, FUNCTION_2, FUNCTION_3, FUNCTION_4, FUNCTION_5, FUNCTION_6, FUNCTION_7, FUNCTION_8, FUNCTION_9
    }

    companion object {
        private const val DEVICE_MODEL_ARG = "device_model"

        @JvmStatic
        fun newInstance(deviceModel: DeviceModel) =
            FncRgbControlFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DEVICE_MODEL_ARG, deviceModel)
                }
            }
    }
}