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

    fun isConnected(deviceModel: DeviceModel): Boolean? {
        val device = connectedDevices.firstOrNull { it.address == deviceModel.macAddress }
        device?.let { connectionManager?.isConnected(device) }
        return connectionManager?.isConnected(device)
    }

    fun connect(deviceModel: DeviceModel) {
        Log.d(TAG, "connect")
        val device = bluetoothAdapter.getRemoteDevice(deviceModel.macAddress)
        connectionManager?.connect(device)
        connectedDevices.add(device)
    }

    fun disconnect(deviceModel: DeviceModel) {
        var device = connectedDevices.firstOrNull { it.address == deviceModel.macAddress }
        if (device != null) {
            connectedDevices.remove(device)
        } else {
            device = bluetoothAdapter.getRemoteDevice(deviceModel.macAddress)
        }
        device?.let { connectionManager?.disconnect(it) }
    }

    fun setLightness(deviceModel: DeviceModel, @IntRange(from = 0, to = 255) lightness: Int) {
        var device = connectedDevices.firstOrNull { it.address == deviceModel.macAddress }
        if (device == null) {
            device = bluetoothAdapter.getRemoteDevice(deviceModel.macAddress)
            connectedDevices.add(device)
        }
        device?.let { connectionManager?.setLightness(it, lightness) }
    }

    fun setColor(deviceModel: DeviceModel, @ColorInt color: Int) {
        val device = bluetoothAdapter.getRemoteDevice(deviceModel.macAddress)
        connectionManager?.setColor(device, color)
    }

    fun setColor(deviceModel: DeviceModel, colorModel: CctColorEntity) {
        val device = bluetoothAdapter.getRemoteDevice(deviceModel.macAddress)
        connectionManager?.setColor(device, colorModel)
    }

    fun setFunction(deviceModel: DeviceModel, typeSaber: Int, command: String) {
        val device = bluetoothAdapter.getRemoteDevice(deviceModel.macAddress)
        connectionManager?.setFunction(device, typeSaber, command)
    }

    fun setSpeed(deviceModel: DeviceModel, speed: Int) {
        val device = bluetoothAdapter.getRemoteDevice(deviceModel.macAddress)
        connectionManager?.setSpeed(device, speed)
    }

    private val bleConnectionManagerCallback = object : BleConnectionManagerCallback {
        override fun onBatteryValue(bluetoothDevice: BluetoothDevice, batteryValue: Int) {
            //viewModel.setBatteryValue(bluetoothDevice, batteryValue)
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

    private fun sendConnectionState(bluetoothDevice: BluetoothDevice, state: Boolean) {
        val intent = Intent(ACTION_CONNECTION_STATE)
        intent.putExtra(ACTION_CONNECTION_STATE, BleServiceState.Connection(bluetoothDevice, state))
        sendBroadcast(intent)
    }




    companion object {
        const val TAG = "BleConnectionService"

        const val ACTION_BLE_STATE = "action_ble_state"
        const val EXTRA_BLE_STATE = "extra_state"

        const val ACTION_CONNECTION_STATE = "action_connection_state"
        const val ACTION_BATTERY_VALUE = "action_battery_value"
    }
}