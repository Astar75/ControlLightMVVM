package com.astar.osterrig.controllightmvvm.service.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;

import com.astar.osterrig.controllightmvvm.model.data.CctColorEntity;
import com.astar.osterrig.controllightmvvm.utils.Constants;



import java.util.Locale;
import java.util.UUID;

import no.nordicsemi.android.ble.BleManager;
import no.nordicsemi.android.ble.PhyRequest;

class SaberBleManager extends BleManager {


    public static final UUID SERVICE_BATTERY_UUID = UUID.fromString("7b20eb75-30bf-40fd-9580-59770fcd0a53");
    public static final UUID BATTERY_CHAR_UUID = UUID.fromString("b0a53ff0-3f99-4ce6-9d6d-a1cc3277251a");

    public static final UUID SERVICE_SABER_UUID = UUID.fromString("d4d4dc12-0493-44fa-bc55-477388a6565c");
    public static final UUID LIGHTNESS_CHAR_UUID = UUID.fromString("879928f7-7b16-4c85-bf73-48f141198c83");
    public static final UUID COLOR_CHAR_UUID = UUID.fromString("879928f7-6a26-4c85-bf73-48f141198c83");
    public static final UUID FUNCTION_CHAR_UUID = UUID.fromString("879928f7-6a36-4c85-bf73-48f141198c83");
    public static final UUID SPEED_CHAR_UUID = UUID.fromString("879928f7-6a46-4c85-bf73-48f141198c83");

    private BluetoothGattCharacteristic lightnessGattCharacteristic;
    private BluetoothGattCharacteristic colorGattCharacteristic;
    private BluetoothGattCharacteristic functionGattCharacteristic;
    private BluetoothGattCharacteristic speedGattCharacteristic;
    private BluetoothGattCharacteristic batteryGattCharacteristic;

    public SaberBleManager(@NonNull Context context) {
        super(context);
        Log.d("SaberBleManager", "connect");
    }

    public void disconnectDevice() {
        disconnect();
    }

    public void setLightness(int lightness) {
        Log.d("SaberBleManager", "setLightness:  " + lightness);
        writeCharacteristic(
                lightnessGattCharacteristic, String.valueOf(lightness).getBytes())
                .split()
                .enqueue();
    }

    public void setColor(int color) {
        final String colorStr = String.format(Locale.getDefault(), "r%dg%db%d", Color.red(color), Color.green(color), Color.blue(color));
        writeCharacteristic(
                colorGattCharacteristic, colorStr.getBytes())
                .split()
                .enqueue();
    }

    public void setColor(CctColorEntity colorModel) {
        final String colorStr = String.format(Locale.getDefault(), "r%dg%db%dw%d",
                colorModel.getRed(),
                colorModel.getGreen(),
                colorModel.getBlue(),
                colorModel.getWhite()
        );
        writeCharacteristic(
                colorGattCharacteristic, colorStr.getBytes())
                .split()
                .enqueue();
    }

    /**
     * 1 - TC, 2 - RGB, 3 - WALS
     * @param typeSaber тип лампы RGB, TC или WALS
     * @param command команда для включения функции
     */
    public void setFunction(int typeSaber, String command) {
        switch (typeSaber) {
            case Constants.TypeSaber.RGB:
            case Constants.TypeSaber.TC:
                writeCharacteristic(functionGattCharacteristic, command.getBytes())
                        .split()
                        .enqueue();
                break;
            case Constants.TypeSaber.WALS:
                writeCharacteristic(functionGattCharacteristic, ("$" + command + "##").getBytes())
                        .split()
                        .enqueue();
                break;
        }

    }

    public void setSpeed(int speed) {
        writeCharacteristic(
                speedGattCharacteristic, String.valueOf(speed).getBytes())
                .split()
                .enqueue();
    }

    @NonNull
    @Override
    protected BleManagerGattCallback getGattCallback() {
        return new SaberManagerCallback();
    }

    private class SaberManagerCallback extends BleManagerGattCallback {

        @Override
        protected void initialize() {
            beginAtomicRequestQueue()
                    .add(requestMtu(507)
                            .with((device, mtu) -> Log.i("Initialize", "MTU set to " + mtu))
                            .fail((device, status) -> Log.w("Initialize", "Request MTU not supported: " + status)))
                    .add(setPreferredPhy(PhyRequest.PHY_LE_1M_MASK, PhyRequest.PHY_LE_1M_MASK, PhyRequest.PHY_OPTION_NO_PREFERRED)
                            .fail((device, status) -> Log.i("Initialize", "")))
                    .done(device -> Log.d("Initialize", "Target initialized"))
                    .enqueue();
        }

        @Override
        protected boolean isRequiredServiceSupported(@NonNull BluetoothGatt gatt) {
            BluetoothGattService serviceBattery = gatt.getService(SERVICE_BATTERY_UUID);
            BluetoothGattService serviceSaber = gatt.getService(SERVICE_SABER_UUID);

            if (serviceSaber != null && serviceBattery != null) {
                batteryGattCharacteristic = serviceBattery.getCharacteristic(BATTERY_CHAR_UUID);
                lightnessGattCharacteristic = serviceSaber.getCharacteristic(LIGHTNESS_CHAR_UUID);
                colorGattCharacteristic = serviceSaber.getCharacteristic(COLOR_CHAR_UUID);
                functionGattCharacteristic = serviceSaber.getCharacteristic(FUNCTION_CHAR_UUID);
                speedGattCharacteristic = serviceSaber.getCharacteristic(SPEED_CHAR_UUID);
            }

            return lightnessGattCharacteristic != null && colorGattCharacteristic != null;
        }

        @Override
        protected void onDeviceDisconnected() {
            batteryGattCharacteristic = null;
            lightnessGattCharacteristic = null;
            colorGattCharacteristic = null;
            functionGattCharacteristic = null;
            speedGattCharacteristic = null;
        }
    }
}