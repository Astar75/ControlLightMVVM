package com.astar.osterrig.controllightmvvm.model.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "devices")
@Parcelize
data class DeviceModel(
    @PrimaryKey
    @ColumnInfo(name = "mac_address")
    val macAddress: String,
    val name: String,
    val typeSaber: Int,
    @ColumnInfo(name = "group_name")
    val groupName: String,
) : Parcelable {
    @Ignore
    var isChecked = false
    @Ignore
    var isConnected: Boolean = false
    @Ignore
    var batteryLevel: Int = 0
}