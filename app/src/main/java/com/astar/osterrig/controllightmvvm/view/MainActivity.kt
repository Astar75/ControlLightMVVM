package com.astar.osterrig.controllightmvvm.view

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.ActivityMainBinding
import com.astar.osterrig.controllightmvvm.utils.LocationPermissionStatus
import com.astar.osterrig.controllightmvvm.utils.getLocationPermissionStatus

internal class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mNavigationManager: NavigationManager
    val navigationManager: NavigationManager
        get() = mNavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mNavigationManager = NavigationManager.newInstance()
    }

    override fun onStart() {
        super.onStart()
        mNavigationManager.attachManager(supportFragmentManager)
        mNavigationManager.navigateToDeviceList()

        enableBluetooth()
        checkLocationPermissions()
    }

    override fun onStop() {
        super.onStop()
        mNavigationManager.detachManager()
    }

    private fun checkLocationPermissions() {
        when (getLocationPermissionStatus(true)) {
            is LocationPermissionStatus.Granted -> {
                Log.d(MainActivity::class.java.simpleName, "Permission granted")
            }
            else -> {
                Log.d(MainActivity::class.java.simpleName, "Permission denied")
                requestLocationPermission()
            }
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

    companion object {
        const val REQUEST_LOCATION_PERMISSION = 1000
        const val REQUEST_ENABLE_BLUETOOTH = 1001
    }
}