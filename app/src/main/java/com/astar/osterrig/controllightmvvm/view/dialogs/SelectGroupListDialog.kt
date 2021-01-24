package com.astar.osterrig.controllightmvvm.view.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.DialogSelectGroupListBinding
import com.astar.osterrig.controllightmvvm.view.adapters.AddSaberToGroupAdapter

class SelectGroupListDialog: DialogFragment() {

    private lateinit var binding: DialogSelectGroupListBinding
    private lateinit var groupList: ArrayList<String>
    private lateinit var adapter: AddSaberToGroupAdapter
    private var callback: Callback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            groupList = it.getSerializable(GROUP_LIST_ARGS) as ArrayList<String>
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogSelectGroupListBinding.inflate(layoutInflater)
        adapter = AddSaberToGroupAdapter()
        val alertDialog = AlertDialog.Builder(requireContext()).apply {
            setTitle(null)
            setMessage(null)
            setView(binding.root)
        }.create()

        alertDialog.setOnShowListener { initViews() }

        return alertDialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        callback = null
    }

    private fun initViews() {
        adapter.addData(groupList)
        binding.dialogTitle.text = getString(R.string.title_dialog_select_group)
        binding.recyclerGroup.addItemDecoration(DividerItemDecoration(
            requireContext(),
            RecyclerView.VERTICAL
        ))
        binding.recyclerGroup.setHasFixedSize(true)
        binding.recyclerGroup.adapter = adapter
        binding.btnOk.setOnClickListener {
            callback?.onSelectGroup(adapter.getSelected())
            dismiss()
        }
    }

    fun addCallback(callback: Callback) {
        this.callback = callback
    }

    interface Callback {
        fun onSelectGroup(groupName: String?)
    }

    companion object {
        private const val GROUP_LIST_ARGS: String = "group_list"

        fun newInstance(groupList: ArrayList<String>): SelectGroupListDialog {
            val args = Bundle()
            args.putSerializable(GROUP_LIST_ARGS, groupList)
            val fragment = SelectGroupListDialog()
            fragment.arguments = args
            return fragment
        }
    }
}