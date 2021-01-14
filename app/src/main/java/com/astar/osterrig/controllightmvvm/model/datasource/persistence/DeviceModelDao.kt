package com.astar.osterrig.controllightmvvm.model.datasource.persistence

import androidx.room.*
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel

@Dao
interface DeviceModelDao {

    @Query("SELECT * FROM devices")
    suspend fun getDevices() : List<DeviceModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDevice(device: DeviceModel)

    @Delete
    suspend fun deleteDevice(device: DeviceModel)

    @Update
    suspend fun updateDevice(device: DeviceModel)

    @Query("SELECT DISTINCT group_name FROM devices WHERE group_name IS NOT NULL")
    suspend fun getGroups(): List<String>

    @Query("SELECT * FROM devices WHERE group_name = :groupName")
    suspend fun getDevicesFromGroup(groupName: String): List<DeviceModel>

    @Query("SELECT * FROM devices WHERE mac_address = :macAddress")
    suspend fun getDeviceByMacAddress(macAddress: String) : DeviceModel?
}
