package com.astar.osterrig.controllightmvvm.view.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.astar.osterrig.controllightmvvm.databinding.ItemGroupBinding

class AddSaberToGroupAdapter : RecyclerView.Adapter<AddSaberToGroupAdapter.ViewHolder>() {

    private val data = arrayListOf<String>()
    private var checkedPosition: Int = 0

    fun addData(data: ArrayList<String>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun getSelected(): String? {
        if (checkedPosition != -1) {
            return data[checkedPosition]
        }
        return null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemGroupBinding =
            ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(val binding: ItemGroupBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            if (checkedPosition == -1) {
                binding.root.setBackgroundColor(Color.TRANSPARENT)
            } else {
                if (checkedPosition == adapterPosition) {
                    binding.root.setBackgroundColor(Color.GRAY)
                } else {
                    binding.root.setBackgroundColor(Color.TRANSPARENT)
                }
            }
            binding.tvNameGroup.text = item
            binding.root.setOnClickListener {
                binding.root.setBackgroundColor(Color.GRAY)
                if (checkedPosition != adapterPosition) {
                    notifyItemChanged(checkedPosition)
                    checkedPosition = adapterPosition
                }
            }
        }
    }
}