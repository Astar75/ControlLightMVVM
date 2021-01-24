package com.astar.osterrig.controllightmvvm.view.screen_groups

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.astar.osterrig.controllightmvvm.model.data.AppState
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.view.base.BaseViewModel
import kotlinx.coroutines.launch
import kotlin.math.log

internal class GroupListViewModel(private val interactor: GroupListInteractorImplementation) : BaseViewModel() {

    private val mLiveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val groupRenderDataList: MutableList<Pair<String, List<DeviceModel>>> = ArrayList()

    override fun handleError(error: Throwable) {
        mLiveDataForViewToObserve.value = AppState.Error(error)
        cancelJob()
    }

    fun getDevicesData() {
        viewModelScope.launch {
            val devices = interactor.getDevices()
            mLiveDataForViewToObserve.value = AppState.SuccessDevices(devices)
        }
    }

    fun getGroupsData() {
        viewModelScope.launch {
            groupRenderDataList.clear()
            val groupNameList: List<String> = interactor.getGroupList()
            for (groupName in groupNameList) {
                if (groupName.isNotEmpty()) {
                    val devicesListInGroup: List<DeviceModel> =
                        interactor.getDevicesFromGroup(groupName)
                    groupRenderDataList.add(Pair(groupName, devicesListInGroup))
                }
            }

            mLiveDataForViewToObserve.value = AppState.Success(groupRenderDataList)
        }
    }

    fun createGroup(nameGroup: String, data: List<DeviceModel>) {
        viewModelScope.launch {
            interactor.createGroup(nameGroup, data)
        }
    }

    fun renameDialog(oldGroupName: String, newGroupName: String) {
        viewModelCoroutineScope.launch {
            interactor.renameGroup(oldGroupName, newGroupName)
            getGroupsData()
        }
    }

    fun removeGroup(nameGroup: String) {
        viewModelScope.launch {
            interactor.removeGroup(nameGroup)
        }
    }

    fun subscribe(): LiveData<AppState> {
        return mLiveDataForViewToObserve
    }


    companion object {
        const val TAG = "GroupListViewModel"
    }
}