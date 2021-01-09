package com.astar.osterrig.controllightmvvm.view.screen_fnc_control

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.SeekBar
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.FragmentFncControlBinding
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.model.data.FunctionWals
import com.astar.osterrig.controllightmvvm.utils.listeners.SeekBarChangeListener
import com.astar.osterrig.controllightmvvm.utils.listeners.SpinnerItemSelectedListener
import com.astar.osterrig.controllightmvvm.utils.setBackgroundTint
import com.astar.osterrig.controllightmvvm.utils.toPercentValue
import com.astar.osterrig.controllightmvvm.view.adapters.SpinnerOptionAdapter
import com.astar.osterrig.controllightmvvm.view.adapters.SpinnerOptionColorAdapter
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment
import com.astar.osterrig.controllightmvvm.view.dialogs.SelectColorDialog
import com.astar.osterrig.controllightmvvm.view.dialogs.SelectFunctionDialog
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_fnc_control.*
import org.koin.android.viewmodel.ext.android.viewModel

internal class FncControlFragment : BaseFragment<FncControlViewModel>() {

    override val mModel: FncControlViewModel by viewModel()
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
        updateFunctionCells()
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

        binding.btnFunctionOne.setOnClickListener(selectClickListener)
        binding.btnFunctionTwo.setOnClickListener(selectClickListener)
        binding.btnFunctionThree.setOnClickListener(selectClickListener)
        binding.btnFunctionFour.setOnClickListener(selectClickListener)
        binding.btnFunctionFive.setOnClickListener(selectClickListener)
        binding.btnFunctionSix.setOnClickListener(selectClickListener)
        binding.btnFunctionSeven.setOnClickListener(selectClickListener)
        binding.btnFunctionEight.setOnClickListener(selectClickListener)
        binding.btnFunctionNine.setOnClickListener(selectClickListener)

        binding.previewFunction.setOnClickListener { rotateFunction() }

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
        mModel.rotationFunction.observe(viewLifecycleOwner, { binding.previewFunction.rotation = if (it) 0F else 180F })

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
        })

        mModel.lightnessPreview.observe(viewLifecycleOwner, { binding.tvLightnessIndicator.toPercentValue(it, 255) })
        mModel.speedPreview.observe(viewLifecycleOwner, { binding.tvSpeedIndicator.toPercentValue(it, 2500) })
    }

    private fun updateFunctionIcon(iconDrawable: Int?, imageView: ImageView) {
        iconDrawable?.let { id ->
            Glide.with(this).load(id).into(imageView)
        }
    }

    private fun updateFunctionControlUi(functionWals: FunctionWals) {
        binding.spParamsTwo.setSelection(if (functionWals.isSmooth) 0 else 1, true)
        binding.sbLightness.progress = functionWals.lightness
        binding.sbSpeed.progress = functionWals.speed
    }

    private fun updateFunctionView(functionWals: FunctionWals) {
        val countColors = spParamsOne.selectedItem.toString().toInt()
        val colors = functionWals.colorArray.toList().subList(0, countColors)
        binding.previewFunction.setSmooth(functionWals.isSmooth)
        binding.previewFunction.setColors(colors)
    }

    private fun openSelectColorDialog() {
        val dialog = SelectColorDialog.newInstance(null)
        dialog.show(childFragmentManager, "select_color_dialog")
        dialog.addCallback(colorDialogListener)
    }

    private val colorDialogListener = object : SelectColorDialog.OnClickListener {
        override fun onClick(color: Int) {
            setColorToItemFunction(color)
            mModel.updateSelectedCell()
        }
    }

    private fun setColorToItemFunction(color: Int) {
        val selectedColorItem = binding.spParamsThree.selectedItemPosition
        mModel.setColorToItemFunction(selectedFunctionCell.ordinal, selectedColorItem, color)
        updateOptionSpinners()
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
        updateOptionSpinners()
    }

    private val longClickListener = View.OnLongClickListener {
        openSelectFunctionDialog(it.id)
        true
    }

    private val seekBarChangeListener = object: SeekBarChangeListener() {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            if (seekBar == binding.sbLightness) {
                mModel.setLightness(progress)
            } else if (seekBar == binding.sbSpeed) {
                mModel.setSpeed(progress)
            }
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            super.onStopTrackingTouch(seekBar)
            if (seekBar == binding.sbLightness) {
                mModel.setLightnessFunction(selectedFunctionCell.ordinal, seekBar.progress)
            } else if (seekBar == binding.sbSpeed) {
                mModel.setSpeedFunction(selectedFunctionCell.ordinal, seekBar.progress)
            }
        }
    }

    private val optionAdapterOneListener = object : SpinnerItemSelectedListener() {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            updateOptionSpinners()
            mModel.updateSelectedCell()
        }
    }

    private val optionAdapterTwoListener = object : SpinnerItemSelectedListener() {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedItem = binding.spParamsTwo.selectedItem.toString()
            Log.d("FncFragment", "onItemSelected: $selectedItem")
            when (selectedItem) {
                "Smooth" -> mModel.setSmooth(selectedFunctionCell.ordinal, true)
                "Sharp" -> mModel.setSmooth(selectedFunctionCell.ordinal, false)
            }
            updateOptionSpinners()
            mModel.updateSelectedCell()
        }
    }

    private val optionAdapterThreeListener = object : SpinnerItemSelectedListener() {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            updateOptionSpinners()
        }
    }

    private fun updateOptionSpinners() {
        when(binding.spParamsOne.selectedItem.toString().toInt()) {
            4 -> { optionAdapterThree.addElementsColor(mModel.getStringAndColorArray(resources.getStringArray(R.array.function_item_color_4))) }
            8 -> { optionAdapterThree.addElementsColor(mModel.getStringAndColorArray(resources.getStringArray(R.array.function_item_color_8))) }
            else -> { optionAdapterThree.addElementsColor(mModel.getStringAndColorArray(resources.getStringArray(R.array.function_item_color_2))) }
        }
    }

    private fun openSelectFunctionDialog(viewId: Int) {
        val dialog = SelectFunctionDialog.newInstance(viewId)
        dialog.addCallback(selectFunctionCallback)
        dialog.show(childFragmentManager, "select_function_dialog")
    }

    private val selectFunctionCallback = object : SelectFunctionDialog.OnClickListener {
        override fun onClick(item: Triple<Int, String, Int>, viewId: Int) {
            when (viewId) {
                binding.btnFunctionOne.id -> { mModel.setFunctionToCell(0, code = item.first, name = item.second, icon = item.third) }
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

    private fun rotateFunction() {
        mModel.rotateFunction()
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