package com.astar.osterrig.controllightmvvm.view.screen_groups

import com.astar.osterrig.controllightmvvm.model.data.DeviceModel

interface GroupListInteractor {

    suspend fun getGroupList(): List<String>
    suspend fun getDeviceFromGroup(nameGroup: String): List<DeviceModel>
    suspend fun getDevices(): List<DeviceModel>
    suspend fun createGroup(nameGroup: String, sabers: List<DeviceModel>)
}