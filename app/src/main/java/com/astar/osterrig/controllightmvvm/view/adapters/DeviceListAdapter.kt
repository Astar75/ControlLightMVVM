package com.astar.osterrig.controllightmvvm.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.astar.osterrig.controllightmvvm.databinding.ItemDeviceListBinding
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel

class DeviceListAdapter : RecyclerView.Adapter<DeviceListAdapter.ViewHolder>() {

    private val mData: MutableList<DeviceModel> = mutableListOf()
    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(deviceModel: DeviceModel)
        fun onItemActionConnectClick(deviceModel: DeviceModel)
        fun onItemActionPowerClick(deviceModel: DeviceModel)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

    fun removeOnItemClickListener() {
        itemClickListener = null
    }

    inner class ViewHolder(private val binding: ItemDeviceListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(deviceModel: DeviceModel) {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(deviceModel)
            }
            binding.tvConnect.setOnClickListener {
                itemClickListener?.onItemActionConnectClick(deviceModel)
            }
            binding.ivBtnPower.setOnClickListener {
                itemClickListener?.onItemActionPowerClick(deviceModel)
            }
            binding.tvNameDevice.text = deviceModel.name
            binding.tvAddressDevice.text = if (deviceModel.groupName.isNotEmpty()) deviceModel.groupName else "Ungrouped"
        }
    }

    inner class DiffUtilCallback(
        private val oldList: List<DeviceModel>,
        private val newList: List<DeviceModel>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].macAddress == newList[newItemPosition].macAddress
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].groupName == newList[newItemPosition].groupName ||
                    oldList[oldItemPosition].name == newList[newItemPosition].name
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return Change(
                oldItem,
                newItem
            )
        }
    }

    data class Change(
        val oldData: DeviceModel,
        val newData: DeviceModel
    )

    private fun createCombinedPayload(payloads: List<Change>): Change {
        assert(payloads.isNotEmpty())
        val firstChange = payloads.first()
        val lastChange = payloads.last()

        return Change(firstChange.oldData, lastChange.newData)
    }

    fun addData(deviceModels: List<DeviceModel>) {
        val diffUtilCallback = DiffUtilCallback(mData, deviceModels)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
        mData.clear()
        mData.addAll(deviceModels)
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemDeviceListBinding =
            ItemDeviceListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deviceModel = mData[position]
        holder.bind(deviceModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val combinedChange = createCombinedPayload(payloads as List<Change>)
            val oldData = combinedChange.oldData
            val newData = combinedChange.newData

            /**
             * Изменить значение батареи
             */
        }
    }

    override fun getItemCount(): Int = mData.size
}