package com.astar.osterrig.controllightmvvm.view.dialogs

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.astar.osterrig.controllightmvvm.databinding.DialogRenameBinding
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel

class RenameDialog : DialogFragment() {

    private lateinit var binding: DialogRenameBinding

    private var title: String = ""
    private var message: String = ""
    private var type: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(TITLE_ARGS, "")
            message = it.getString(CURRENT_TEXT_ARGS, "")
            type = it.getString(TYPE, "")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogRenameBinding.inflate(layoutInflater)
        val alertDialog = AlertDialog.Builder(requireContext()).apply {
            setTitle(null)
            setMessage(null)
            setView(binding.root)
        }.create()
        alertDialog.setOnShowListener { initView() }
        return alertDialog
    }

    private fun initView() {
        binding.tvDialogTitle.text = title
        binding.etNewName.setText(message)
        binding.btnOk.setOnClickListener {
            sendNewName()
            dismiss()
        }
    }

    private fun sendNewName() {
        val text = binding.etNewName.text.toString()
        val intent = Intent()
        intent.putExtra(EXTRA_NEW_NAME, text)
        intent.putExtra(EXTRA_TYPE, type)
        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
    }

    companion object {
        private const val TAG = "RenameDialog"
        private const val TITLE_ARGS = "title"
        private const val CURRENT_TEXT_ARGS = "current_text"
        private const val TYPE = "type"

        const val TYPE_SABER = "type_saber"
        const val TYPE_GROUP = "type_group"

        const val EXTRA_NEW_NAME = "new_name"
        const val EXTRA_TYPE = "type"

        fun newInstance(title: String, currentText: String, type: String): RenameDialog {
            val args = Bundle()
            args.putString(TITLE_ARGS, title)
            args.putString(CURRENT_TEXT_ARGS, currentText)
            args.putString(TYPE, type)
            val fragment = RenameDialog()
            fragment.arguments = args
            return fragment
        }
    }
}