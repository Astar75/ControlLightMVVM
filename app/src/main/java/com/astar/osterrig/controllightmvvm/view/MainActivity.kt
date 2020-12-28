package com.astar.osterrig.controllightmvvm.view

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.ActivityMainBinding
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.service.BleConnectionService
import com.astar.osterrig.controllightmvvm.utils.LocationPermissionStatus
import com.astar.osterrig.controllightmvvm.utils.getLocationPermissionStatus

internal class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mNavigationManager: NavigationManager
    private lateinit var mService: BleConnectionService
    private var mBound = false

    val navigationManager: NavigationManager
        get() = mNavigationManager

    private val mServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BleConnectionService.LocalBinder
            mService = binder.service
            mBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mNavigationManager = NavigationManagerImplementation.newInstance()
    }

    override fun onStart() {
        super.onStart()
        mNavigationManager.attachManager(supportFragmentManager)
        mNavigationManager.navigateToDeviceList(false)

        enableBluetooth()
        checkLocationPermissions()
        startBleConnectionService()
    }

    override fun onStop() {
        super.onStop()
        mNavigationManager.detachManager()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopBleConnectionService()
    }

    private fun startBleConnectionService() {
        val intent = Intent(this, BleConnectionService::class.java)
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun stopBleConnectionService() {
        unbindService(mServiceConnection)
    }

    private fun checkLocationPermissions() {
        if (getLocationPermissionStatus(true) != LocationPermissionStatus.Granted) {
            requestLocationPermission()
        } else {
            Toast.makeText(this, getString(R.string.toast_location_permission), Toast.LENGTH_SHORT).show()
        }
    }

    private fun enableBluetooth() {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled) {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, REQUEST_ENABLE_BLUETOOTH)
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION_PERMISSION
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BLUETOOTH && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, getString(R.string.toast_bt_must_be_enabled), Toast.LENGTH_LONG)
                .show()
            enableBluetooth()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        checkLocationPermissions()
    }

    fun connect(deviceModel: DeviceModel) {
        Log.d("MainActivity", "connect")
        mService.connect(deviceModel)
    }

    fun disconnect(deviceModel: DeviceModel) {
        mService.disconnect(deviceModel)
    }

    fun setLightness(deviceModel: DeviceModel, lightness: Int) {
        mService.setLightness(deviceModel, lightness)
    }

    fun setColor(deviceModel: DeviceModel, color: String) {
        mService.setColor(deviceModel, color)
    }

    companion object {
        const val REQUEST_LOCATION_PERMISSION = 1000
        const val REQUEST_ENABLE_BLUETOOTH = 1001
    }
}