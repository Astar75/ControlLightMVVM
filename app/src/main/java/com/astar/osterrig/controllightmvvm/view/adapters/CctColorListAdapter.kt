package com.astar.osterrig.controllightmvvm.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.astar.osterrig.controllightmvvm.databinding.ItemCctColorBinding
import com.astar.osterrig.controllightmvvm.model.data.CctColorEntity

class CctColorListAdapter : RecyclerView.Adapter<CctColorListAdapter.ViewHolder>() {

    private val data: MutableList<CctColorEntity> = mutableListOf()
    private var onItemClickListener: OnItemClickListener? = null

    fun addData(data: List<CctColorEntity>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    fun removeOnItemClickListener() {
        this.onItemClickListener = null
    }

    interface OnItemClickListener {
        fun onItemClickListener(item: CctColorEntity)
    }

    inner class ViewHolder(private val binding: ItemCctColorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CctColorEntity) {
            binding.root.setOnClickListener {
                onItemClickListener?.onItemClickListener(item)
            }

            Log.d("CctAdapter", "${item.backgroundCell}")
            binding.tvCctTitle.text = item.kelvin
            binding.cell.setBackgroundColor(item.backgroundCell)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCctColorBinding = ItemCctColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = data.size
}