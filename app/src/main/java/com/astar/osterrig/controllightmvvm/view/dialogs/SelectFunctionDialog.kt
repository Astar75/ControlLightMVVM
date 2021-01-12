package com.astar.osterrig.controllightmvvm.view.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.DialogSelectFunctionBinding
import com.astar.osterrig.controllightmvvm.view.adapters.FunctionListAdapter

class SelectFunctionDialog : DialogFragment() {

    private lateinit var binding: DialogSelectFunctionBinding
    private lateinit var adapter: FunctionListAdapter
    private var onClickListener: OnClickListener? = null
    private var functionList: MutableList<Triple<Int, String, Int>> = arrayListOf()
    private var viewId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewId = it.getInt(VIEW_ID_ARGS)
            functionList = it.getSerializable(FUNCTION_LIST_ARGS) as ArrayList<Triple<Int, String, Int>>
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogSelectFunctionBinding.inflate(layoutInflater)
        adapter = FunctionListAdapter()
        val alertDialogBuilder = AlertDialog.Builder(requireContext()).apply {
            setTitle(null)
            setMessage(null)
            setView(binding.root)
        }

        return alertDialogBuilder.create().apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.argb(0, 100, 100, 100)))
            setOnShowListener {
                binding.btnCancel.setOnClickListener { dismiss() }
                binding.titleDialog.text = getString(R.string.title_dialog_functions)
                binding.recyclerFunctionList.adapter = adapter
                binding.recyclerFunctionList.setHasFixedSize(true)
                binding.recyclerFunctionList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter.addData(functionList)
                adapter.setOnItemClickListener(object : FunctionListAdapter.OnItemClickListener {
                    override fun onItemClickListener(item: Triple<Int, String, Int>) {
                        onClickListener?.onClick(item, viewId)
                        dismiss()
                    }
                })
            }
        }
    }

    fun addCallback(callback: OnClickListener) {
        onClickListener = callback
    }

    interface OnClickListener {
        fun onClick(item: Triple<Int, String, Int>, viewId: Int)
    }

    companion object {
        private const val VIEW_ID_ARGS = "view_id"
        private const val FUNCTION_LIST_ARGS = "function_list"

        fun newInstance(viewId: Int, functionList: ArrayList<Triple<Int, String, Int>>): SelectFunctionDialog {
            val fragment = SelectFunctionDialog()
            val args = Bundle()
            args.putInt(VIEW_ID_ARGS, viewId)
            args.putSerializable(FUNCTION_LIST_ARGS, functionList)
            fragment.arguments = args
            return fragment
        }
    }
}