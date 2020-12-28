package com.astar.osterrig.controllightmvvm.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun Activity.getLocationPermissionStatus(userAskedPermissionBefore: Boolean): LocationPermissionStatus {
    // Permission not granted
    return if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // User already denied permission
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            LocationPermissionStatus.DeniedAlready
        } else {
            // User denied permission and selected option "Don't ask again"
            if (userAskedPermissionBefore) {
                // If User was asked permission before and denied
                LocationPermissionStatus.DeniedWithDontAsk
            } else {
                // User asked permission for the first time
                LocationPermissionStatus.FirstTimeAsking
            }
        }
    } else {
        // Permission already granted
        LocationPermissionStatus.Granted
    }
}

sealed class LocationPermissionStatus {
    object Granted: LocationPermissionStatus()
    object DeniedAlready: LocationPermissionStatus()
    object DeniedWithDontAsk: LocationPermissionStatus()
    object FirstTimeAsking: LocationPermissionStatus()
}