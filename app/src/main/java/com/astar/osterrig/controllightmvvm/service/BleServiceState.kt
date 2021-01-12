package com.astar.osterrig.controllightmvvm.service

import android.bluetooth.BluetoothDevice
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


sealed class BleServiceState: Parcelable {

    @Parcelize data class Battery(val device: BluetoothDevice, val batteryValue: Int): BleServiceState()
    @Parcelize data class Connection(val device: BluetoothDevice, val connectionState: Boolean): BleServiceState()

    @Parcelize data class Connecting(val device: BluetoothDevice): BleServiceState()
    @Parcelize data class Connected(val device: BluetoothDevice): BleServiceState()
    @Parcelize data class Disconnected(val device: BluetoothDevice): BleServiceState()
    @Parcelize data class FailedToConnect(val device: BluetoothDevice): BleServiceState()
    @Parcelize data class IsConnected(val device: BluetoothDevice): BleServiceState()

}