package com.astar.osterrig.controllightmvvm.view

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.*
import android.os.Bundle
import android.os.IBinder
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

internal class MainActivity : AppCompatActivity() {

    // TODO: 12.01.2021 Сохранение найденных ламп в базу данных

    private lateinit var mBinding: ActivityMainBinding
    private var mService: BleConnectionService? = null
    private var mBound = false
    lateinit var mNavigationManager: NavigationManager

    private val viewModel: MainActivityViewModel by viewModels()

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
        addObservers()
    }

    override fun onStop() {
        super.onStop()
        mNavigationManager.detachManager()
        unbindService(mServiceConnection)
        removeObservers()
        removeReceiver()
    }

    private fun addObservers() {
        viewModel.connect.observe(this, { if (it.second) connect(it.first) else disconnect(it.first) })
        viewModel.lightness.observe(this, { setLightness(deviceModel = it.first, lightness = it.second) })
        viewModel.speed.observe(this, { setSpeed(deviceModel = it.first, speed = it.second) })
        viewModel.color.observe(this, { setColor(deviceModel = it.first, color = it.second) })
        viewModel.colorTc.observe(this, { setColor(deviceModel = it.first, warm = it.second, cold = it.third) })
        viewModel.cctColor.observe(this, { setColor(deviceModel = it.first, colorModel = it.second) })
        viewModel.function.observe(this, { setFunction(deviceModel = it.first, typeSaber = it.second, command = it.third) })
        viewModel.batteryLevel.observe(this, { requestBatteryLevel(it) })
    }

    private fun removeObservers() {
        viewModel.connect.removeObservers(this)
        viewModel.lightness.removeObservers(this)
        viewModel.speed.removeObservers(this)
        viewModel.color.removeObservers(this)
        viewModel.cctColor.removeObservers(this)
        viewModel.connectionState.removeObservers(this)
        viewModel.function.removeObservers(this)
        viewModel.batteryLevel.removeObservers(this)
    }

    private fun addReceiver() {
        val intentFilter = IntentFilter().apply {
            addAction(BleConnectionService.ACTION_BLE_STATE)
            addAction(BleConnectionService.ACTION_BATTERY_VALUE)
        }
        registerReceiver(connectionServiceReceiver, intentFilter)
    }

    private fun removeReceiver() {
        unregisterReceiver(connectionServiceReceiver)
    }

    private val connectionServiceReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == BleConnectionService.ACTION_BLE_STATE) {
                val connectionState = intent.getParcelableExtra<BleServiceState>(BleConnectionService.EXTRA_BLE_STATE)
                connectionState?.let {  viewModel.setConnectionState(it) }
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
            Toast.makeText(this, getString(R.string.toast_location_permission), Toast.LENGTH_SHORT)
                .show()
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

    private fun connect(deviceModel: List<DeviceModel>) {
        mService?.connect(deviceModel)
    }

    private fun disconnect(deviceModel: List<DeviceModel>) {
        mService?.disconnect(deviceModel)
    }

    private fun setLightness(deviceModel: List<DeviceModel>, lightness: Int) {
        mService?.setLightness(deviceModel, lightness)
    }

    private fun setColor(deviceModel: List<DeviceModel>, color: Int) {
        mService?.setColor(deviceModel, color)
    }

    private fun setColor(deviceModel: List<DeviceModel>, warm: Int, cold: Int) {
        mService?.setColor(deviceModel, warm, cold)
    }

    private fun setColor(deviceModel: List<DeviceModel>, colorModel: CctColorEntity) {
        mService?.setColor(deviceModel, colorModel)
    }

    private fun setFunction(deviceModel: List<DeviceModel>, typeSaber: Int, command: String) {
        mService?.setFunction(deviceModel, typeSaber, command)
    }

    private fun setSpeed(deviceModel: List<DeviceModel>, speed: Int) {
        mService?.setSpeed(deviceModel, speed)
    }

    private fun requestBatteryLevel(device: BluetoothDevice) {
        mService?.requestBatteryLevel(device)
    }

    fun requestIsConnected(macAddress: String): Boolean {
        val isConnected = mService?.isConnected(macAddress)
        return isConnected ?: false
    }

    companion object {
        const val REQUEST_LOCATION_PERMISSION = 1000
        const val REQUEST_ENABLE_BLUETOOTH = 1001
    }
}