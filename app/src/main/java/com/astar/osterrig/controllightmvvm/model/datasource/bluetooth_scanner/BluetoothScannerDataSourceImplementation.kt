package com.astar.osterrig.controllightmvvm.model.datasource.bluetooth_scanner

import android.bluetooth.BluetoothDevice
import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat
import no.nordicsemi.android.support.v18.scanner.ScanCallback
import no.nordicsemi.android.support.v18.scanner.ScanResult
import no.nordicsemi.android.support.v18.scanner.ScanSettings


internal class BluetoothScannerDataSourceImplementation : BluetoothScannerDataSource {

    private val mScanner: BluetoothLeScannerCompat by lazy {
        BluetoothLeScannerCompat.getScanner()
    }

    private var mIsScanning: Boolean = false
    private val mScanFailedMutable: MutableLiveData<Int> = MutableLiveData()
    private val mFoundedDeviceList: MutableList<BluetoothDevice> = mutableListOf()
    private val mHandlerDurationScan = Handler()

    private val mScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            if (result.device.name != null && result.device.name.startsWith(SCAN_PREFIX_NAME)) {
                addDeviceToList(result.device)
            }
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            mScanFailedMutable.value = errorCode
            Log.d(TAG, "Scan error $errorCode")
        }
    }

    private fun addDeviceToList(device: BluetoothDevice) {
        val index = mFoundedDeviceList.firstOrNull {
            it.address == device.address
        }
        if (index == null) {
            mFoundedDeviceList.add(device)
            Log.d(TAG, "New device: Name: ${device.name}, Mac Address: ${device.address}")
        }
    }

    private val mSearchDevicesFlow: Flow<List<BluetoothDevice>> = flow {
        while (mIsScanning) {
            emit(mFoundedDeviceList)
            delay(SEND_DEVICES_DURATION)
        }
    }

    override fun searchDevices(): Flow<List<BluetoothDevice>> {
        return mSearchDevicesFlow
    }

    override fun getScanningState(): Boolean = mIsScanning

    @ExperimentalCoroutinesApi
    private val _scanningStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    @ExperimentalCoroutinesApi
    override fun scanState(): StateFlow<Boolean> {
        return _scanningStateFlow
    }

    @ExperimentalCoroutinesApi
    override fun startScan() {
        mIsScanning = true
        _scanningStateFlow.value = true
        val settings = ScanSettings.Builder()
            .setLegacy(false)
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setUseHardwareBatchingIfSupported(false)
            .build()
        mScanner.startScan(null, settings, mScanCallback)

        mHandlerDurationScan.postDelayed({
            stopScan()
        }, SCAN_DURATION)
    }

    @ExperimentalCoroutinesApi
    override fun stopScan() {
        mIsScanning = false
        _scanningStateFlow.value = false
        mScanner.stopScan(mScanCallback)
    }

    companion object {
        const val TAG = "BluetoothScanner"
        const val SCAN_PREFIX_NAME = "Osterrig"
        const val SCAN_DURATION = 20000L
        const val SEND_DEVICES_DURATION = 1000L
    }
}