package com.astar.osterrig.controllightmvvm.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.astar.osterrig.controllightmvvm.databinding.ItemGroupDevicesBinding
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel

class GroupDeviceAdapter : RecyclerView.Adapter<GroupDeviceAdapter.ViewHolder>() {

    private val dataDevices: MutableList<Pair<String, List<DeviceModel>>> = mutableListOf()

    fun addData(groupList: List<Pair<String, List<DeviceModel>>>) {
        dataDevices.clear()
        dataDevices.addAll(groupList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemGroupDevicesBinding =
            ItemGroupDevicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataDevices[position])
    }

    override fun getItemCount(): Int = dataDevices.size

    inner class ViewHolder(val binding: ItemGroupDevicesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(groupModel: Pair<String, List<DeviceModel>>) {
            binding.tvGroupName.text = groupModel.first
            binding.tvTypeSaber.text = "Count sabers: ${groupModel.second.size}"
        }
    }
}