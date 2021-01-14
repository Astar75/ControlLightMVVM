package com.astar.osterrig.controllightmvvm.view.screen_groups

import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.model.repository.DeviceModelRepository

class GroupListInteractorImplementation(
    private val deviceModelRepository: DeviceModelRepository
) : GroupListInteractor {

    override suspend fun getGroupList(): List<String> =
        deviceModelRepository.getGroups()

    override suspend fun getDeviceFromGroup(nameGroup: String) =
        deviceModelRepository.getDeviceFromGroup(nameGroup)

    override suspend fun getDevices(): List<DeviceModel> =
        deviceModelRepository.getDevices()

    override suspend fun createGroup(nameGroup: String, sabers: List<DeviceModel>) {
        deviceModelRepository.createGroup(nameGroup, sabers)
    }
}