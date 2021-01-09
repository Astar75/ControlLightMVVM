package com.astar.osterrig.controllightmvvm.view

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.ActivityMainBinding
import com.astar.osterrig.controllightmvvm.model.data.CctColorEntity
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.service.BleConnectionService
import com.astar.osterrig.controllightmvvm.service.BleServiceState
import com.astar.osterrig.controllightmvvm.utils.LocationPermissionStatus
import com.astar.osterrig.controllightmvvm.utils.getLocationPermissionStatus
import com.astar.osterrig.controllightmvvm.view.viewmodels.ActivityViewModel

internal class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private var mService: BleConnectionService? = null
    private var mBound = false
    lateinit var mNavigationManager: NavigationManager

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
        addReceiver()
    }

    override fun onStop() {
        super.onStop()
        mNavigationManager.detachManager()
        unbindService(mServiceConnection)
        removeReceiver()
    }

    private fun addReceiver() {
        val intentFilter = IntentFilter().apply {
            addAction(BleConnectionService.ACTION_CONNECTION_STATE)
            addAction(BleConnectionService.ACTION_BATTERY_VALUE)
        }
        registerReceiver(connectionServiceReceiver, intentFilter)
    }

    private fun removeReceiver() {
        unregisterReceiver(connectionServiceReceiver)
    }

    private val connectionServiceReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == BleConnectionService.ACTION_CONNECTION_STATE) {
                val dataAction = intent.getParcelableExtra<BleServiceState>(BleConnectionService.ACTION_CONNECTION_STATE)
                when (dataAction) {
                    is BleServiceState.Connection -> {

                    }
                    is BleServiceState.Battery -> {

                    }
                }
            }
        }
    }

    private fun startBleConnectionService() {
        val intent = Intent(this, BleConnectionService::class.java)
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
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
        mService?.connect(deviceModel)
    }

    fun disconnect(deviceModel: DeviceModel) {
        mService?.disconnect(deviceModel)
    }

    fun setLightness(deviceModel: DeviceModel, lightness: Int) {
        mService?.setLightness(deviceModel, lightness)
    }

    fun setColor(deviceModel: DeviceModel, color: Int) {
        mService?.setColor(deviceModel, color)
    }

    fun setColor(deviceModel: DeviceModel, colorModel: CctColorEntity) {
        mService?.setColor(deviceModel, colorModel)
    }

    fun setFunction(deviceModel: DeviceModel, typeSaber: Int, command: String) {
        mService?.setFunction(deviceModel, typeSaber, command)
    }

    companion object {
        const val REQUEST_LOCATION_PERMISSION = 1000
        const val REQUEST_ENABLE_BLUETOOTH = 1001
    }
}