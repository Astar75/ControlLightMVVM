package com.astar.osterrig.controllightmvvm.view.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.DialogGroupSabersBinding
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.view.adapters.DeviceForGroupAdapter

class GroupSabersDialog : DialogFragment() {

    private lateinit var binding: DialogGroupSabersBinding
    private lateinit var adapter: DeviceForGroupAdapter
    private val deviceList: MutableList<DeviceModel> = ArrayList()

    private var callback: Callback? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            deviceList.addAll(it.getSerializable(DEVICE_LIST_ARGS) as ArrayList<DeviceModel>)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogGroupSabersBinding.inflate(layoutInflater)
        val alertDialog = AlertDialog.Builder(requireContext()).apply {
            setView(binding.root)
            setTitle(null)
            setMessage(null)
        }.create()
        alertDialog.setOnShowListener { initViews() }
        return alertDialog
    }

    private fun initViews() {
        adapter = DeviceForGroupAdapter()
        adapter.addData(deviceList)
        binding.tvTitle.text = getString(R.string.title_dialog_create_group)
        binding.recyclerDevices.setHasFixedSize(true)
        binding.recyclerDevices.adapter = adapter
        binding.btnOk.setOnClickListener {
            val selectedDevices = adapter.getSelectedDevices()
            for (device in selectedDevices) {
                Log.d("GroupSabersDialog", "Device: ${device.macAddress}")
            }

            // createGroup(selectedSabers)
            // dismiss()
        }
    }

    private fun createGroup(data: List<DeviceModel>) {
        val nameGroup = binding.etNameGroup.text.toString()
        callback?.createGroup(nameGroup, data)
    }

    fun addCallback(callback: Callback) {
        this.callback = callback
    }

    interface Callback {
        fun createGroup(nameGroup: String, data: List<DeviceModel>)
    }

    companion object {
        private const val DEVICE_LIST_ARGS = "device_list"

        fun newInstance(deviceList: ArrayList<DeviceModel>): GroupSabersDialog {
            val args = Bundle()
            args.putSerializable(DEVICE_LIST_ARGS, deviceList)
            val fragment = GroupSabersDialog()
            fragment.arguments = args
            return fragment
        }
    }
}