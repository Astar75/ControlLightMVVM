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
import androidx.lifecycle.ViewModelProvider
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

        override fun onConnected(bluetoothDevice: BluetoothDevice) {
            sendConnectionState(bluetoothDevice, true)
        }

        override fun onDisconnected(bluetoothDevice: BluetoothDevice) {
            sendConnectionState(bluetoothDevice, false)
        }
    }

    private fun sendConnectionState(bluetoothDevice: BluetoothDevice, state: Boolean) {
        val intent = Intent(ACTION_CONNECTION_STATE)
        intent.putExtra(ACTION_CONNECTION_STATE, BleServiceState.Connection(bluetoothDevice, state))
        sendBroadcast(intent)
    }



    companion object {
        const val TAG = "BleConnectionService"
        const val ACTION_CONNECTION_STATE = "action_connection_state"
        const val ACTION_BATTERY_VALUE = "action_battery_value"
    }
}