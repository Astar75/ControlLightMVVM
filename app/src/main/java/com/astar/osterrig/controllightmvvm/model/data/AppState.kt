package com.astar.osterrig.controllightmvvm.model.data

sealed class AppState {

    data class Success(val data: List<*>) : AppState()
    data class SuccessDevices(val data: List<*>): AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}