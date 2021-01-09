package com.astar.osterrig.controllightmvvm.model.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "devices")
@Parcelize
data class DeviceModel(

    @PrimaryKey
    @ColumnInfo(name = "mac_address")
    val macAddress: String,
    val name: String,
    @ColumnInfo(name = "group_name")
    val typeSaber: Int,
    val groupName: String
) : Parcelable