package com.astar.osterrig.controllightmvvm.service

import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.annotation.IntRange
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.service.bluetooth.BleConnectionManager
import com.astar.osterrig.controllightmvvm.service.bluetooth.BleConnectionManagerImplementation

class BleConnectionService : Service() {

    private val bluetoothAdapter: BluetoothAdapter by lazy {
        BluetoothAdapter.getDefaultAdapter()
    }

    private var connectionManager: BleConnectionManager? = null

    inner class LocalBinder : Binder() {
        val service: BleConnectionService
            get() = this@BleConnectionService
    }

    override fun onCreate() {
        super.onCreate()
        connectionManager = BleConnectionManagerImplementation.newInstance(this)
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "Запуск сервиса")
        return LocalBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "Остановка сервиса")
        stopSelf()
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectionManager = null
    }

    fun connect(deviceModel: DeviceModel) {
        Log.d(TAG, "connect")
        val device = bluetoothAdapter.getRemoteDevice(deviceModel.macAddress)
        connectionManager?.connect(device)
    }

    fun disconnect(deviceModel: DeviceModel) {
        val device = bluetoothAdapter.getRemoteDevice(deviceModel.macAddress)
        connectionManager?.disconnect(device)
    }

    fun setLightness(deviceModel: DeviceModel, @IntRange(from = 0, to = 255) lightness: Int) {
        val device = bluetoothAdapter.getRemoteDevice(deviceModel.macAddress)
        connectionManager?.setLightness(device, lightness)
    }

    fun setColor(deviceModel: DeviceModel, color: String) {
        val device = bluetoothAdapter.getRemoteDevice(deviceModel.macAddress)
        connectionManager?.setColor(device, color)
    }

    companion object {
        const val TAG = "BleConnectionService"
    }
}