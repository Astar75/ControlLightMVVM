package com.astar.osterrig.controllightmvvm.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.astar.osterrig.controllightmvvm.databinding.ItemDeviceListBinding
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.chauthai.swipereveallayout.ViewBinderHelper
import kotlinx.android.synthetic.main.item_device_list.view.*


class DeviceListAdapter : RecyclerView.Adapter<DeviceListAdapter.ViewHolder>() {

    private var mData: MutableList<DeviceModel> = ArrayList()
    private var itemClickListener: OnItemClickListener? = null
    private lateinit var binding: ItemDeviceListBinding

    private val viewBinderHelper = ViewBinderHelper()

    fun addData(deviceModels: List<DeviceModel>) {
        Log.e("Adapter", "addData: ADAPTER DATA")
        for (device in deviceModels) {
            Log.d("Adapter", "    ${device.macAddress}, ${device.batteryLevel}, ${device.isConnected}")
        }
        val diffUtilCallback = DiffUtilCallback(mData, deviceModels)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
        mData = ArrayList(deviceModels)
        diffUtilResult.dispatchUpdatesTo(this)
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
            binding.tvNameDevice.text = deviceModel.name
            binding.tvAddressDevice.text =
                if (deviceModel.groupName.isNotEmpty()) deviceModel.groupName else "Ungrouped"

            binding.ivBattery.setBatteryLevel(deviceModel.batteryLevel)

            if (deviceModel.isConnected) {
                binding.tvConnect.visibility = View.INVISIBLE
                binding.viewColor.visibility = View.VISIBLE
                binding.ivBattery.visibility = View.VISIBLE
                binding.ivBtnPower.visibility = View.VISIBLE
            } else {
                binding.tvConnect.visibility = View.VISIBLE
                binding.viewColor.visibility = View.INVISIBLE
                binding.ivBattery.visibility = View.INVISIBLE
                binding.ivBtnPower.visibility = View.INVISIBLE
            }
        }
    }

    private fun createCombinedPayload(payloads: List<Change>): Change {
        assert(payloads.isNotEmpty())
        val firstChange = payloads.first()
        val lastChange = payloads.last()

        return Change(firstChange.oldData, lastChange.newData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemDeviceListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deviceModel = mData[position]
        viewBinderHelper.setOpenOnlyOne(true)
        viewBinderHelper.bind(holder.itemView.swipelayout, deviceModel.macAddress)
        viewBinderHelper.closeLayout(deviceModel.macAddress)

        binding.tvConnect.setOnClickListener {
            itemClickListener?.onItemActionConnectClick(
                deviceModel
            )
        }
        binding.containerItemSaber.setOnClickListener { itemClickListener?.onItemClick(deviceModel) }

        holder.bind(deviceModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val combinedChange = createCombinedPayload(payloads as List<Change>)
            val oldData = combinedChange.oldData
            val newData = combinedChange.newData

            /*if (oldData.batteryLevel == newData.batteryLevel) {
                binding.ivBattery.setBatteryLevel(newData.batteryLevel)
            }

            if (oldData.isConnected == newData.isConnected) {
                if (newData.isConnected) {
                    binding.tvConnect.visibility = View.INVISIBLE
                    binding.viewColor.visibility = View.VISIBLE
                    binding.ivBattery.visibility = View.VISIBLE
                    binding.ivBtnPower.visibility = View.VISIBLE
                } else {
                    binding.tvConnect.visibility = View.VISIBLE
                    binding.viewColor.visibility = View.INVISIBLE
                    binding.ivBattery.visibility = View.INVISIBLE
                    binding.ivBtnPower.visibility = View.INVISIBLE
                }
            }*/
        }
    }

    override fun getItemCount(): Int = mData.size

    private data class Change(
        val oldData: DeviceModel,
        val newData: DeviceModel
    )


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
            /*oldList[oldItemPosition] == newList[newItemPosition]*/
            return oldList[oldItemPosition].name == newList[newItemPosition].name &&
                    oldList[oldItemPosition].groupName == newList[newItemPosition].groupName &&
                    oldList[oldItemPosition].batteryLevel == newList[newItemPosition].batteryLevel &&
                    oldList[oldItemPosition].isConnected == newList[newItemPosition].isConnected
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

    interface OnItemClickListener {
        fun onItemClick(deviceModel: DeviceModel)
        fun onItemActionConnectClick(deviceModel: DeviceModel)
        fun onItemActionPowerClick(deviceModel: DeviceModel)
        fun onAddSaberToGroup(deviceModel: DeviceModel)
        fun onRemoveSaber(deviceModel: DeviceModel)
        fun onRenameSaber(deviceModel: DeviceModel)
    }
}