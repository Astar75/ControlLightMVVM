package com.astar.osterrig.controllightmvvm.service.bluetooth

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.util.Log
import no.nordicsemi.android.ble.observer.ConnectionObserver

class BleConnectionManagerImplementation(val context: Context) :
    BleConnectionManager, ConnectionObserver {

    private val bleManagers: MutableMap<BluetoothDevice, SaberBleManager> = mutableMapOf()
    private val managedDevices: MutableList<BluetoothDevice> = mutableListOf()

    override fun connect(device: BluetoothDevice) {

        if (managedDevices.contains(device))
            return
        managedDevices.add(device)
        var manager: SaberBleManager? = bleManagers[device]
        if (manager == null) {
            manager = SaberBleManager(context)
            bleManagers[device] = manager
            manager.setConnectionObserver(this)
        }

        manager.connect(device)
            .retry(2, 100)
            .useAutoConnect(true)
            .timeout(10000)
            .fail { _, _ ->
                managedDevices.remove(device)
                bleManagers.remove(device)
            }
            .enqueue()
    }

    override fun disconnect(device: BluetoothDevice) {
        val manager = bleManagers[device]
        manager?.let {
            if (it.isConnected) it.disconnect().enqueue()
        }
    }

    override fun setLightness(device: BluetoothDevice, lightness: Int) {
        val manager = bleManagers[device]
        manager?.let {
            if (it.isConnected) it.setLightness(lightness)
        }
    }

    override fun setColor(device: BluetoothDevice, color: String) {
        val manager = bleManagers[device]
        manager?.let {
            if (it.isConnected) it.setColor(color)
        }
    }

    override fun onDeviceConnecting(device: BluetoothDevice) {

    }

    override fun onDeviceConnected(device: BluetoothDevice) {
        Log.d(TAG, "onDeviceConnected: Соединение успешно ${device.address}")
    }

    override fun onDeviceFailedToConnect(device: BluetoothDevice, reason: Int) {

    }

    override fun onDeviceReady(device: BluetoothDevice) {

    }

    override fun onDeviceDisconnecting(device: BluetoothDevice) {
        Log.d(TAG, "onDeviceDisconnecting: Устройство разъединено ${device.address}")
    }

    override fun onDeviceDisconnected(device: BluetoothDevice, reason: Int) {

    }

    companion object {
        const val TAG = "BleConnectionManager"

        fun newInstance(context: Context) = BleConnectionManagerImplementation(context)
    }
}