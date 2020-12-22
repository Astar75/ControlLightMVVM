package com.astar.osterrig.controllightmvvm.view.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.DialogAddDeviceBinding
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import kotlinx.android.synthetic.main.dialog_add_device.*
import kotlinx.android.synthetic.main.dialog_add_device.view.*

class AddDeviceDialog : DialogFragment() {

    private var mCallback: CallbackListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_device, null)
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(view)
        val dialog = dialogBuilder.create()
        dialog.setOnShowListener {
            view.btnAddDevice.setOnClickListener {
                addDevice(
                    view.etName.text.toString(),
                    view.etMac.text.toString(),
                    view.etNameGroup.text.toString()
                )
            }
        }
        return dialog
    }

    private fun addDevice(name: String, macAddress: String, nameGroup: String) {
        if (name.isNotEmpty() || macAddress.isNotEmpty()) {
            mCallback?.onClick(DeviceModel(name = name, macAddress = macAddress, groupName = nameGroup))
        }
    }

    fun addCallback(callback: CallbackListener) {
        mCallback = callback
    }

    interface CallbackListener {
        fun onClick(device: DeviceModel)
    }

    companion object {
        fun newInstance() = AddDeviceDialog()
    }

}