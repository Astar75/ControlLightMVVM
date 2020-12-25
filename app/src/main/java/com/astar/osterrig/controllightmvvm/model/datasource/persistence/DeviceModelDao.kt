package com.astar.osterrig.controllightmvvm.model.datasource.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import kotlinx.coroutines.Deferred

@Dao
interface DeviceModelDao {

    @Query("SELECT * FROM devices")
    fun getDevices() : LiveData<List<DeviceModel>>

    @Insert
    suspend fun insertDevice(device: DeviceModel)

    @Delete
    suspend fun deleteDevice(device: DeviceModel)

    @Query("SELECT DISTINCT group_name FROM devices WHERE group_name IS NOT NULL")
    suspend fun getGroups() : List<String>

    @Query("SELECT * FROM devices WHERE group_name = :groupName")
    suspend fun getDevicesFromGroup(groupName: String): List<DeviceModel>

    @Query("SELECT * FROM devices WHERE mac_address = :macAddress")
    suspend fun getDeviceByMacAddress(macAddress: String) : DeviceModel?
}
