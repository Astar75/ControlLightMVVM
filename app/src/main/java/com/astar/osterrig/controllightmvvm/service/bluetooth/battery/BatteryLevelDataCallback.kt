package com.astar.osterrig.controllightmvvm.service.bluetooth.battery

import android.bluetooth.BluetoothDevice
import no.nordicsemi.android.ble.callback.profile.ProfileDataCallback
import no.nordicsemi.android.ble.data.Data

abstract class BatteryLevelDataCallback : ProfileDataCallback {
    override fun onInvalidDataReceived(device: BluetoothDevice, data: Data) {

    }
}