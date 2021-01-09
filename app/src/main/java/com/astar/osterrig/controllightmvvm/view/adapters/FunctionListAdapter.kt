package com.astar.osterrig.controllightmvvm.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.astar.osterrig.controllightmvvm.databinding.ItemFunctionListBinding

class FunctionListAdapter: RecyclerView.Adapter<FunctionListAdapter.ViewHolder>() {

    private val data: MutableList<Triple<Int, String, Int>> = mutableListOf()
    private var onItemClickListener: OnItemClickListener? = null

    fun addData(data: List<Triple<Int, String, Int>>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClickListener(item: Triple<Int, String, Int>)
    }

    inner class ViewHolder(private val binding: ItemFunctionListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Triple<Int, String, Int>) {
            binding.root.setOnClickListener {
                onItemClickListener?.onItemClickListener(item)
            }
            binding.nameEffect.text = item.second
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemFunctionListBinding =
            ItemFunctionListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = data.size
}