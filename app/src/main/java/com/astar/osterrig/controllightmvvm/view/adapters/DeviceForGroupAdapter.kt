package com.astar.osterrig.controllightmvvm.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.ItemGroupSelectedDeviceBinding
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel

class DeviceForGroupAdapter : RecyclerView.Adapter<DeviceForGroupAdapter.ViewHolder>() {
    private val backupData: MutableList<DeviceModel> = mutableListOf()
    private val data: MutableList<DeviceModel> = mutableListOf()
    private val selectedDevice: MutableList<DeviceModel> = mutableListOf()

    fun addData(data: List<DeviceModel>) {
        this.data.clear()
        this.data.addAll(data)
        this.backupData.clear()
        this.backupData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemGroupSelectedDeviceBinding = ItemGroupSelectedDeviceBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val device = data[position]
        holder.bind(device)
    }

    override fun getItemCount(): Int = data.size

    fun getSelectedDevices() = selectedDevice

    inner class ViewHolder(val binding: ItemGroupSelectedDeviceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DeviceModel) {
            binding.tvSaberName.text = String.format(
                "%s - %s",
                item.name,
                if (item.groupName.isEmpty()) "Ungrouped" else item.groupName
            )

            if (item.isChecked) {
                binding.itemContainer.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.itemContainer.context,
                        R.color.list_selected_item
                    )
                )
            } else {
                binding.itemContainer.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.itemContainer.context,
                        R.color.rgb_default_transparent
                    )
                )
            }

            binding.itemContainer.setOnClickListener {
                onChecked(item)
            }
        }

        private fun onChecked(item: DeviceModel) {
            item.isChecked = !item.isChecked
            if (item.isChecked) {
                if (selectedDevice.isEmpty()) {
                    data.removeAll { it.typeSaber != item.typeSaber }
                }
                selectedDevice.add(item)
            } else {
                selectedDevice.remove(item)
                if (selectedDevice.isEmpty()) {
                    data.clear()
                    data.addAll(backupData)
                }
            }
            notifyDataSetChanged()
        }
    }
}


