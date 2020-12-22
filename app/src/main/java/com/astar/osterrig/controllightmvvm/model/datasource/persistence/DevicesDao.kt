package com.astar.osterrig.controllightmvvm.model.datasource.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel

@Dao
interface DevicesDao {

    @Insert
    fun insertDevice(device: DeviceModel)

    @Query("SELECT * FROM devices")
    fun getDevices() : LiveData<List<DeviceModel>>

    @Query("SELECT * FROM devices WHERE mac_address = :macAddress")
    fun getDeviceByMacAddress(macAddress: String) : DeviceModel
}
