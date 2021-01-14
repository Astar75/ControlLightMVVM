package com.astar.osterrig.controllightmvvm.view.adapters

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.astar.osterrig.controllightmvvm.databinding.ItemGroupSelectedDeviceBinding
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel

class DeviceForGroupAdapter : RecyclerView.Adapter<DeviceForGroupAdapter.ViewHolder>() {

    private val backupData: MutableList<DeviceModel> = mutableListOf()
    private val data: MutableList<DeviceModel> = mutableListOf()
    private val selectedDevice: MutableList<DeviceModel> = mutableListOf()

    private var callback: Callback? = null

    fun addData(data: List<DeviceModel>) {
        this.data.clear()
        this.data.addAll(data)
        this.backupData.clear()
        this.backupData.addAll(data)
        notifyDataSetChanged()
    }

    fun getSelectedDevices() = selectedDevice

    fun addCallback(callback: Callback) {
        this.callback = callback
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
        holder.binding.cbSelectDevice.setOnCheckedChangeListener { buttonView, isChecked ->
            data[position].isChecked = isChecked
            Handler().postDelayed({
                hideOrShowItems()
                notifyDataSetChanged()
            }, 10)

        }
        if (device.isChecked) {
            if (!selectedDevice.contains(device)) selectedDevice.add(device)
        } else {
            if (selectedDevice.contains(device)) selectedDevice.remove(device)
        }

    }

    override fun getItemCount(): Int = data.size

    private fun hideOrShowItems() {
        if (selectedDevice.size > 0) {
            val type = selectedDevice[0].typeSaber
            data.removeAll { it.typeSaber != type }
        } else {
            data.clear()
            data.addAll(backupData)
        }

        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemGroupSelectedDeviceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DeviceModel) {
            binding.cbSelectDevice.text = item.name
            binding.cbSelectDevice.isChecked = item.isChecked

        }
    }

    interface Callback {
        fun onSelectItems(data: List<DeviceModel>)
    }
}


