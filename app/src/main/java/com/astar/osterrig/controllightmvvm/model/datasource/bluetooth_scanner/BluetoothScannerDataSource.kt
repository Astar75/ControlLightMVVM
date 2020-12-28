package com.astar.osterrig.controllightmvvm.model.datasource.bluetooth_scanner

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.LiveData
import com.astar.osterrig.controllightmvvm.model.data.AppState
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

interface BluetoothScannerDataSource {

    fun searchDevices(): Flow<List<BluetoothDevice>>
    fun getScanningState(): Boolean
    fun startScan()
    fun stopScan()
}