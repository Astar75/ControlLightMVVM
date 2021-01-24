package com.astar.osterrig.controllightmvvm.service

import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import com.astar.osterrig.controllightmvvm.model.data.CctColorEntity
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.service.bluetooth.BleConnectionManager
import com.astar.osterrig.controllightmvvm.service.bluetooth.BleConnectionManagerCallback
import com.astar.osterrig.controllightmvvm.service.bluetooth.BleConnectionManagerImplementation

class BleConnectionService : Service() {

    private val bluetoothAdapter: BluetoothAdapter by lazy {
        BluetoothAdapter.getDefaultAdapter()
    }

    private var connectionManager: BleConnectionManager? = null
    private val connectedDevices: MutableList<BluetoothDevice> = mutableListOf()

    inner class LocalBinder : Binder() {
        val service: BleConnectionService
            get() = this@BleConnectionService
    }

    override fun onCreate() {
        super.onCreate()
        connectionManager = BleConnectionManagerImplementation.newInstance(this)
        connectionManager?.addCallback(bleConnectionManagerCallback)
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "Запуск сервиса")
        return LocalBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "Остановка сервиса")
        connectionManager?.disconnectAll()
        connectionManager?.removeCallback()
        connectionManager = null
        stopSelf()
        return super.onUnbind(intent)
    }

    fun isConnected(macAddress: String): Boolean {
        val device = bluetoothAdapter.getRemoteDevice(macAddress)
        val isConnected = connectionManager?.isConnected(device)
        return isConnected ?: false
    }

    fun connect(deviceModel: List<DeviceModel>) {
        for (device in deviceModel) {
            val bluetoothDevice = bluetoothAdapter.getRemoteDevice(device.macAddress)
            connectionManager?.connect(bluetoothDevice)
            if (connectedDevices.firstOrNull { device.macAddress == it.address } == null) {
                connectedDevices.add(bluetoothDevice)
            }
        }
    }

    private fun getOrCreateBluetoothDevice(device: DeviceModel): BluetoothDevice? {
        var bluetoothDevice = connectedDevices.firstOrNull { it.address == device.macAddress }
        if (bluetoothDevice != null) {
            connectedDevices.remove(bluetoothDevice)
        } else {
            bluetoothDevice = bluetoothAdapter.getRemoteDevice(device.macAddress)
        }
        return bluetoothDevice
    }

    fun disconnect(deviceModel: List<DeviceModel>) {
        for (device in deviceModel) {
            val bluetoothDevice = getOrCreateBluetoothDevice(device)
            bluetoothDevice?.let { connectionManager?.disconnect(it) }
        }
    }

    fun setLightness(deviceModel: List<DeviceModel>, @IntRange(from = 0, to = 255) lightness: Int) {
        for (device in deviceModel) {
            val bluetoothDevice = getOrCreateBluetoothDevice(device)
            bluetoothDevice?.let { connectionManager?.setLightness(it, lightness) }
        }
    }

    fun setColor(deviceModel: List<DeviceModel>, @ColorInt color: Int) {
        for (device in deviceModel) {
            val bluetoothDevice = getOrCreateBluetoothDevice(device)
            bluetoothDevice?.let { connectionManager?.setColor(it, color) }
        }
    }

    fun setColor(deviceModel: List<DeviceModel>, warm: Int, cold: Int) {
        for (device in deviceModel) {
            val bluetoothDevice = getOrCreateBluetoothDevice(device)
            bluetoothDevice?.let { connectionManager?.setColor(it, warm, cold) }
        }
    }

    fun setColor(deviceModel: List<DeviceModel>, colorModel: CctColorEntity) {
        for (device in deviceModel) {
            val bluetoothDevice = getOrCreateBluetoothDevice(device)
            bluetoothDevice?.let { connectionManager?.setColor(it, colorModel) }
        }
    }

    fun setFunction(deviceModel: List<DeviceModel>, typeSaber: Int, command: String) {
        for (device in deviceModel) {
            val bluetoothDevice = getOrCreateBluetoothDevice(device)
            bluetoothDevice?.let { connectionManager?.setFunction(it, typeSaber, command) }
        }
    }

    fun setSpeed(deviceModel: List<DeviceModel>, speed: Int) {
        for (device in deviceModel) {
            val bluetoothDevice = getOrCreateBluetoothDevice(device)
            bluetoothDevice?.let { connectionManager?.setSpeed(it, speed) }
        }
    }

    fun requestBatteryLevel(device: BluetoothDevice) {
        connectionManager?.requestBatteryLevel(device)
    }

    private val bleConnectionManagerCallback = object : BleConnectionManagerCallback {
        override fun onBatteryValue(bluetoothDevice: BluetoothDevice, batteryValue: Int) {
            val serviceState = BleServiceState.Battery(bluetoothDevice, batteryValue)
            sendStatusBroadcast(serviceState)
        }

        override fun onSaberCurrentColor(bluetoothDevice: BluetoothDevice, colorStr: String) {
            //viewModel.setSaberCurrentColor(bluetoothDevice, colorStr)
        }

        override fun onCurrentLightness(bluetoothDevice: BluetoothDevice, lightness: Int) {
            //viewModel.setSaberCurrentLightness(bluetoothDevice, lightness)
        }

        override fun onConnecting(bluetoothDevice: BluetoothDevice) {
            val serviceState = BleServiceState.Connecting(bluetoothDevice)
            sendStatusBroadcast(serviceState)
        }

        override fun onConnected(bluetoothDevice: BluetoothDevice) {
            val serviceState = BleServiceState.Connected(bluetoothDevice)
            sendStatusBroadcast(serviceState)
        }

        override fun onDisconnected(bluetoothDevice: BluetoothDevice) {
            val serviceState = BleServiceState.Disconnected(bluetoothDevice)
            sendStatusBroadcast(serviceState)
        }

        override fun onFailedToConnect(bluetoothDevice: BluetoothDevice) {
            val serviceState = BleServiceState.FailedToConnect(bluetoothDevice)
            sendStatusBroadcast(serviceState)
        }

        override fun isConnected(device: BluetoothDevice) {
            val serviceState = BleServiceState.IsConnected(device)
            sendStatusBroadcast(serviceState)
        }
    }

    private fun sendStatusBroadcast(bleServiceState: BleServiceState) {
        val intent = Intent(ACTION_BLE_STATE)
        intent.putExtra(EXTRA_BLE_STATE, bleServiceState)
        sendBroadcast(intent)
    }

    companion object {
        const val TAG = "BleConnectionService"

        const val ACTION_BLE_STATE = "action_ble_state"
        const val ACTION_BATTERY_VALUE = "action_battery_value"
        const val EXTRA_BLE_STATE = "extra_state"
    }
}