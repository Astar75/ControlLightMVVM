package com.astar.osterrig.controllightmvvm.view.dialogs

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.DialogSelectColorBinding

class SelectColorDialog: DialogFragment() {

    private lateinit var binding: DialogSelectColorBinding
    private var color = Color.BLACK

    private var onClickListener: OnClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            color = it.getInt(COLOR_ARGS, Color.BLACK)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogSelectColorBinding.inflate(layoutInflater)
        val alertDialogBuilder = AlertDialog.Builder(requireContext()).apply {
            setTitle(null)
            setMessage(null)
            setView(binding.root)
        }
        return alertDialogBuilder.create().apply {
            setOnShowListener {
                binding.tvTitle.text = getString(R.string.title_dialog_color_picker)
                binding.colorPicker.setInitialColor(color)
                binding.btnSelect.setOnClickListener {
                    onClickListener?.onClick(binding.colorPicker.color)
                    dismiss()
                }
            }
        }
    }

    fun addCallback(callback: OnClickListener) {
        onClickListener = callback
    }

    interface OnClickListener {
        fun onClick(@ColorInt color: Int)
    }

    companion object {
        const val COLOR_ARGS = "color"

        fun newInstance(color: Int?): SelectColorDialog {
            val fragment = SelectColorDialog ()
            color?.let {
                val args = Bundle()
                args.putInt(COLOR_ARGS, it)
                fragment.arguments = args
            }
            return fragment
        }
    }
}