package com.astar.osterrig.controllightmvvm.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.astar.osterrig.controllightmvvm.databinding.ItemGroupDevicesBinding
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.chauthai.swipereveallayout.ViewBinderHelper
import kotlinx.android.synthetic.main.item_device_list.view.*

class GroupDeviceAdapter : RecyclerView.Adapter<GroupDeviceAdapter.ViewHolder>() {

    private val dataDevices: MutableList<Pair<String, List<DeviceModel>>> = mutableListOf()
    private var callback: Callback? = null

    private val viewBinderHelper = ViewBinderHelper()

    fun addData(groupList: List<Pair<String, List<DeviceModel>>>) {
        dataDevices.clear()
        dataDevices.addAll(groupList)
        notifyDataSetChanged()
    }

    fun addCallback(callback: Callback) {
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemGroupDevicesBinding =
            ItemGroupDevicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataDevices[position])
        viewBinderHelper.setOpenOnlyOne(true)
        viewBinderHelper.bind(holder.itemView.swipelayout, dataDevices[position].first)
        viewBinderHelper.closeLayout(dataDevices[position].first)
        holder.itemView.setOnClickListener { callback?.onConnect(dataDevices[position].second) }
    }

    override fun getItemCount(): Int = dataDevices.size

    private fun renameGroup() {
    }

    private fun removeGroup() {
    }

    inner class ViewHolder(val binding: ItemGroupDevicesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(groupModel: Pair<String, List<DeviceModel>>) {
            binding.tvGroupName.text = groupModel.first
            binding.tvTypeSaber.text = "Count sabers: ${groupModel.second.size}"
            binding.tvRename.setOnClickListener { callback?.onRenameGroup(groupModel.first) }
            binding.tvRemove.setOnClickListener { callback?.onRemoveGroup(groupModel.first) }
            binding.containerGroupSaber.setOnClickListener { callback?.onConnect(groupModel.second) }
        }
    }

    interface Callback {
        fun onConnect(groupDeviceList: List<DeviceModel>)
        fun onRenameGroup(currentName: String)
        fun onRemoveGroup(currentName: String)
    }
}