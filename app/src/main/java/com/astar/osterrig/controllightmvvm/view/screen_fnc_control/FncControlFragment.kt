package com.astar.osterrig.controllightmvvm.view.screen_fnc_control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.FragmentFncControlBinding
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.model.data.FunctionWals
import com.astar.osterrig.controllightmvvm.utils.Constants
import com.astar.osterrig.controllightmvvm.utils.DataProvider
import com.astar.osterrig.controllightmvvm.utils.listeners.SeekBarChangeListener
import com.astar.osterrig.controllightmvvm.utils.listeners.SpinnerItemSelectedListener
import com.astar.osterrig.controllightmvvm.utils.setBackgroundTint
import com.astar.osterrig.controllightmvvm.utils.toPercentValue
import com.astar.osterrig.controllightmvvm.view.MainActivityViewModel
import com.astar.osterrig.controllightmvvm.view.adapters.SpinnerOptionAdapter
import com.astar.osterrig.controllightmvvm.view.adapters.SpinnerOptionColorAdapter
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment
import com.astar.osterrig.controllightmvvm.view.dialogs.SelectColorDialog
import com.astar.osterrig.controllightmvvm.view.dialogs.SelectFunctionDialog
import com.bumptech.glide.Glide
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

internal class FncControlFragment : BaseFragment<FncControlViewModel>() {

    override val mModel: FncControlViewModel by viewModel()
    private val controlViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var binding: FragmentFncControlBinding
    private lateinit var deviceModel: DeviceModel

    private lateinit var optionAdapterOne: SpinnerOptionAdapter
    private lateinit var optionAdapterTwo: SpinnerOptionAdapter
    private lateinit var optionAdapterThree: SpinnerOptionColorAdapter

    private var selectedFunctionCell = FunctionCell.FUNCTION_1

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
        binding = FragmentFncControlBinding.inflate(inflater, container, false)
        initView()
        addObservers()
        updateSelectedFunctionCell()
        return binding.root
    }

    private fun initView() {
        with(binding.bottomNavPanel) {
            btnOne.text = context?.getString(R.string.title_tab_rgb_control)
            btnTwo.text = context?.getString(R.string.title_tab_cct_control)
            btnThree.text = context?.getString(R.string.title_tab_ftp_control)
            btnFour.text = context?.getString(R.string.title_tab_fnc_control)

            btnFour.setBackgroundTint(R.color.button_active_color)
            btnOne.setOnClickListener { navigateToRgbControl(deviceModel, true) }
            btnTwo.setOnClickListener { navigateToCctControl(deviceModel) }
            btnThree.setOnClickListener { navigateToFtpForWalsControl(deviceModel) }
        }

        binding.btnFunctionOne.setOnClickListener(selectCellClickListener)
        binding.btnFunctionTwo.setOnClickListener(selectCellClickListener)
        binding.btnFunctionThree.setOnClickListener(selectCellClickListener)
        binding.btnFunctionFour.setOnClickListener(selectCellClickListener)
        binding.btnFunctionFive.setOnClickListener(selectCellClickListener)
        binding.btnFunctionSix.setOnClickListener(selectCellClickListener)
        binding.btnFunctionSeven.setOnClickListener(selectCellClickListener)
        binding.btnFunctionEight.setOnClickListener(selectCellClickListener)
        binding.btnFunctionNine.setOnClickListener(selectCellClickListener)

        binding.previewFunction.setOnClickListener {
            changeDirectionFunction()
            sendFunctionCommand()
        }

        binding.btnFunctionOne.setOnLongClickListener(selectFunctionLongClickListener)
        binding.btnFunctionTwo.setOnLongClickListener(selectFunctionLongClickListener)
        binding.btnFunctionThree.setOnLongClickListener(selectFunctionLongClickListener)
        binding.btnFunctionFour.setOnLongClickListener(selectFunctionLongClickListener)
        binding.btnFunctionFive.setOnLongClickListener(selectFunctionLongClickListener)
        binding.btnFunctionSix.setOnLongClickListener(selectFunctionLongClickListener)
        binding.btnFunctionSeven.setOnLongClickListener(selectFunctionLongClickListener)
        binding.btnFunctionEight.setOnLongClickListener(selectFunctionLongClickListener)
        binding.btnFunctionNine.setOnLongClickListener(selectFunctionLongClickListener)

        binding.sbLightness.setOnSeekBarChangeListener(cellSeekBarChangeListener)
        binding.sbSpeed.setOnSeekBarChangeListener(cellSeekBarChangeListener)

        binding.colorAndFunctionSelector.setOnClickListener {
            openSelectColorDialog()
        }

        optionAdapterOne = SpinnerOptionAdapter(null)
        optionAdapterTwo = SpinnerOptionAdapter(null)
        optionAdapterThree = SpinnerOptionColorAdapter(null)

        optionAdapterOne.addElements(resources.getStringArray(R.array.function_count_colors))
        optionAdapterTwo.addElements(resources.getStringArray(R.array.function_smooth_type))
        optionAdapterThree.addElementsColor(mModel.getStringAndColorArray(resources.getStringArray(R.array.function_item_color_2)))

        binding.spParamsOne.adapter = optionAdapterOne
        binding.spParamsTwo.adapter = optionAdapterTwo
        binding.spParamsThree.adapter = optionAdapterThree

        binding.spParamsOne.onItemSelectedListener = optionAdapterOneListener
        binding.spParamsTwo.onItemSelectedListener = optionAdapterTwoListener
        binding.spParamsThree.onItemSelectedListener = optionAdapterThreeListener
    }

    private fun addObservers() {
        mModel.cellFunctionOne.observe(viewLifecycleOwner, { updateIconFunctionCell(it.icon, binding.btnFunctionOne) })
        mModel.cellFunctionTwo.observe(viewLifecycleOwner, { updateIconFunctionCell(it.icon, binding.btnFunctionTwo) })
        mModel.cellFunctionThree.observe(viewLifecycleOwner, { updateIconFunctionCell(it.icon, binding.btnFunctionThree) })
        mModel.cellFunctionFour.observe(viewLifecycleOwner, { updateIconFunctionCell(it.icon, binding.btnFunctionFour) })
        mModel.cellFunctionFive.observe(viewLifecycleOwner, { updateIconFunctionCell(it.icon, binding.btnFunctionFive) })
        mModel.cellFunctionSix.observe(viewLifecycleOwner, { updateIconFunctionCell(it.icon, binding.btnFunctionSix) })
        mModel.cellFunctionSeven.observe(viewLifecycleOwner, { updateIconFunctionCell(it.icon, binding.btnFunctionSeven) })
        mModel.cellFunctionEight.observe(viewLifecycleOwner, { updateIconFunctionCell(it.icon, binding.btnFunctionEight) })
        mModel.cellFunctionNine.observe(viewLifecycleOwner, { updateIconFunctionCell(it.icon, binding.btnFunctionNine) })

        mModel.selectedFunction.observe(viewLifecycleOwner, {
            updateControlUi(it)
            showToastMessage(it.name)
        })

        mModel.lightnessPreviewIndicator.observe(viewLifecycleOwner, { binding.tvLightnessIndicator.toPercentValue(it, 255) })
        mModel.speedPreviewIndicator.observe(viewLifecycleOwner, { binding.tvSpeedIndicator.toPercentValue(it, 2500) })

        mModel.blockLightnessSeekBar.observe(viewLifecycleOwner, { binding.sbLightness.isEnabled = it })
        mModel.blockSpeedSeekBar.observe(viewLifecycleOwner, { binding.sbSpeed.isEnabled = it })
        mModel.blockSpinnerOne.observe(viewLifecycleOwner, { binding.spParamsOne.isEnabled = it })
        mModel.blockSpinnerTwo.observe(viewLifecycleOwner, { binding.spParamsTwo.isEnabled = it })
        mModel.blockSpinnerThree.observe(viewLifecycleOwner, { binding.spParamsThree.isEnabled = it })
        mModel.blockPreviewFunction.observe(viewLifecycleOwner, { binding.previewFunction.isEnabled = it })
    }

    private fun updateIconFunctionCell(icon: Int, cellImageView: ImageView) {
        Glide.with(this).load(icon).into(cellImageView)
    }

    private fun updateControlUi(functionWals: FunctionWals) {
        val functionIndicatorColors = functionWals.colorArray.toList().subList(0, functionWals.useColors)
        binding.previewFunction.rotation = if (functionWals.isReverse) 0f else 180f
        binding.previewFunction.setSmooth(functionWals.isSmooth)
        binding.previewFunction.setColors(functionIndicatorColors)
        binding.sbLightness.progress = functionWals.lightness
        binding.sbSpeed.progress = functionWals.speed
        when (functionWals.useColors) {
            4 -> { binding.spParamsOne.setSelection(1, true) }
            8 -> { binding.spParamsOne.setSelection(2, true) }
            else -> { binding.spParamsOne.setSelection(0, true) }
        }
        if (functionWals.isSmooth) {
            binding.spParamsTwo.setSelection(0, true)
        } else {
            binding.spParamsTwo.setSelection(1, true)
        }
    }

    private fun selectFunctionCell(viewId: Int) {
        when (viewId) {
            binding.btnFunctionOne.id -> { selectedFunctionCell = FunctionCell.FUNCTION_1 }
            binding.btnFunctionTwo.id -> { selectedFunctionCell = FunctionCell.FUNCTION_2 }
            binding.btnFunctionThree.id -> { selectedFunctionCell = FunctionCell.FUNCTION_3 }
            binding.btnFunctionFour.id -> { selectedFunctionCell = FunctionCell.FUNCTION_4 }
            binding.btnFunctionFive.id -> { selectedFunctionCell = FunctionCell.FUNCTION_5 }
            binding.btnFunctionSix.id -> { selectedFunctionCell = FunctionCell.FUNCTION_6 }
            binding.btnFunctionSeven.id -> { selectedFunctionCell = FunctionCell.FUNCTION_7 }
            binding.btnFunctionEight.id -> { selectedFunctionCell = FunctionCell.FUNCTION_8 }
            binding.btnFunctionNine.id -> { selectedFunctionCell = FunctionCell.FUNCTION_9 }
        }
    }

    private val selectCellClickListener = View.OnClickListener { v ->
        selectFunctionCell(v.id)
        mModel.selectFunctionCell(selectedFunctionCell.ordinal)
        updateSelectedFunctionCell()
        sendFunctionCommand()
    }

    private val selectFunctionLongClickListener = View.OnLongClickListener { v ->
        selectFunctionCell(v.id)
        mModel.selectFunctionCell(selectedFunctionCell.ordinal)
        updateSelectedFunctionCell()
        openSelectFunctionDialog(v.id)
        true
    }

    private fun openSelectFunctionDialog(viewId: Int) {
        val functionList = DataProvider.getWalsFunctionNameList()
        SelectFunctionDialog.newInstance(viewId, functionList).apply {
            addCallback(object : SelectFunctionDialog.OnClickListener {
                override fun onClick(item: Triple<Int, String, Int>, viewId: Int) {
                    mModel.changeFunctionCell(selectedFunctionCell.ordinal, item.first, item.second, item.third)
                }
            })
        }.show(childFragmentManager, "select_function_dialog")

    }

    private val optionAdapterOneListener = object : SpinnerItemSelectedListener() {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val countUseColors = binding.spParamsOne.selectedItem.toString().toInt()
            mModel.changeUseColorsFunction(selectedFunctionCell.ordinal, countUseColors)
            updateSpinners()
            sendFunctionCommand()
        }
    }

    private val optionAdapterTwoListener = object : SpinnerItemSelectedListener() {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val currentItem = binding.spParamsTwo.selectedItem.toString().toLowerCase(Locale.getDefault())
            when (currentItem) {
                "smooth" -> { mModel.changeSmoothFunction(selectedFunctionCell.ordinal, true) }
                "sharp" -> { mModel.changeSmoothFunction(selectedFunctionCell.ordinal, false) }
            }
            updateSpinners()
            sendFunctionCommand()
        }
    }

    private val optionAdapterThreeListener = object : SpinnerItemSelectedListener() {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            updateSpinners()
        }
    }

    private fun updateSpinners() {
        val countUseColors = binding.spParamsOne.selectedItem.toString().toInt()
        when(countUseColors) {
            4 -> { optionAdapterThree.addElementsColor(mModel.getStringAndColorArray(resources.getStringArray(R.array.function_item_color_4))) }
            8 -> { optionAdapterThree.addElementsColor(mModel.getStringAndColorArray(resources.getStringArray(R.array.function_item_color_8))) }
            else -> { optionAdapterThree.addElementsColor(mModel.getStringAndColorArray(resources.getStringArray(R.array.function_item_color_2))) }
        }
    }

    private val cellSeekBarChangeListener = object : SeekBarChangeListener () {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            if (seekBar == binding.sbLightness) {
                mModel.setLightnessIndicator(progress)
                if (progress % 5 == 0) {
                    mModel.changeLightnessFunction(selectedFunctionCell.ordinal, progress)
                    if (fromUser) controlViewModel.setLightness(deviceModel, progress)
                }
            } else if (seekBar == binding.sbSpeed) {
                mModel.setSpeedIndicator(progress)
                if (progress % 5 == 0) {
                    mModel.changeSpeedFunction(selectedFunctionCell.ordinal, progress)
                    if (fromUser) controlViewModel.setSpeed(deviceModel, progress)
                }
            }
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            if (seekBar == binding.sbLightness) {
                mModel.setLightnessIndicator(seekBar.progress)
                mModel.changeLightnessFunction(selectedFunctionCell.ordinal, seekBar.progress)
            } else if (seekBar == binding.sbSpeed) {
                mModel.setSpeedIndicator(seekBar.progress)
                mModel.changeSpeedFunction(selectedFunctionCell.ordinal, seekBar.progress)
            }
            sendFunctionCommand()
        }
    }

    private fun openSelectColorDialog() {
        SelectColorDialog.newInstance(null).apply {
            addCallback(object : SelectColorDialog.OnClickListener {
                override fun onClick(color: Int) {
                    changeColorFunction(color)
                    updateSpinners()
                    sendFunctionCommand()
                }
            })
        }.show(childFragmentManager, "select_color_dialog")
    }

    private fun changeColorFunction(color: Int) {
        val currentColorItem = binding.spParamsThree.selectedItemPosition
        mModel.changeColorFunction(selectedFunctionCell.ordinal, currentColorItem, color)
    }

    private fun changeDirectionFunction() {
        val currentCell = selectedFunctionCell.ordinal
        mModel.changeDirectionFunction(currentCell)
    }

    private fun sendFunctionCommand() {
        val selectedFunction = mModel.selectedFunction.value
        selectedFunction?.let {
            if (it.code != Constants.WalsFunctionCode.NONE) {
                // TODO: 11.01.2021 Сделать настройку "Отправлять скорость перед включением эффекта"
                // controlViewModel.setSpeed(deviceModel, it.speed)
                controlViewModel.setFunction(deviceModel, Constants.TypeSaber.WALS, it)
            }
        }
    }

    private fun updateSelectedFunctionCell() {
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

        const val DEVICE_MODEL_ARG = "device_model"

        @JvmStatic
        fun newInstance(deviceModel: DeviceModel) =
            FncControlFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DEVICE_MODEL_ARG, deviceModel)
                }
            }
    }
}
