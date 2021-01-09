package com.astar.osterrig.controllightmvvm.service

import android.bluetooth.BluetoothDevice
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


sealed class BleServiceState: Parcelable {

    @Parcelize data class Battery(val device: BluetoothDevice, val batteryValue: Int): BleServiceState()
    @Parcelize data class Connection(val device: BluetoothDevice, val connectionState: Boolean): BleServiceState()
}