package com.astar.osterrig.controllightmvvm.view.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.SeekBar
import androidx.fragment.app.DialogFragment
import com.astar.osterrig.controllightmvvm.databinding.DialogFlameSettingsBinding
import com.astar.osterrig.controllightmvvm.utils.listeners.SeekBarChangeListener

class FlameSettingsDialog: DialogFragment() {

    private var cooling: Int = 0
    private var sparking: Int = 0

    private var callback: Callback? = null

    private lateinit var binding: DialogFlameSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cooling = it.getInt(COOLING_ARGS, 0)
            sparking = it.getInt(SPARKING_ARGS, 0)
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogFlameSettingsBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext()).apply {
            setTitle(null)
            setMessage(null)
            setView(binding.root)
        }.create()
        dialog.setOnShowListener { initViews() }
        return dialog
    }

    private fun initViews() {
        binding.btnOk.setOnClickListener {
            callback?.onChangeFlameSettings(cooling, sparking)
            dismiss()
        }
        binding.sbCooling.progress = cooling
        binding.sbSparking.progress = sparking
        binding.sbSparking.setOnSeekBarChangeListener(seekBarChangeListener)
        binding.sbCooling.setOnSeekBarChangeListener(seekBarChangeListener)
    }

    private val seekBarChangeListener = object : SeekBarChangeListener() {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            when (seekBar) {
                binding.sbSparking -> sparking = progress
                binding.sbCooling -> cooling = progress
            }
            if (progress % 5 == 0) {
                callback?.onChangeFlameSettings(cooling, sparking)
            }
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            when (seekBar) {
                binding.sbSparking -> sparking = seekBar.progress
                binding.sbCooling -> cooling = seekBar.progress
            }
            callback?.onChangeFlameSettings(cooling, sparking)
        }
    }

    fun addCallback(callback: Callback) {
        this.callback = callback
    }

    interface Callback {
        fun onChangeFlameSettings(cooling: Int, sparking: Int)
    }

    companion object {
        private const val COOLING_ARGS = "cooling"
        private const val SPARKING_ARGS = "sparking"

        fun newInstance(cooling: Int, sparking: Int): FlameSettingsDialog {
            val args = Bundle()
            args.putInt(COOLING_ARGS, cooling)
            args.putInt(SPARKING_ARGS, sparking)
            val fragment = FlameSettingsDialog ()
            fragment.arguments = args
            return fragment
        }
    }
}