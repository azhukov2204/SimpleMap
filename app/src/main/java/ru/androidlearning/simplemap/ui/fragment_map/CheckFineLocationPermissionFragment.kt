package ru.androidlearning.simplemap.ui.fragment_map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.androidlearning.simplemap.R
import ru.androidlearning.simplemap.core.BaseMVVMFragment

abstract class CheckFineLocationPermissionFragment(@LayoutRes contentLayoutId: Int) : BaseMVVMFragment(contentLayoutId) {

    private val onLocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            doOnLocationChanged(location)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    abstract fun doOnFineLocationPermissionGranted()
    abstract fun doOnLocationChanged(location: Location)

    protected fun checkPermissionToFineLocation() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> getSelfLocation()
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> requestAccessToFineLocationWithDialog()
            else -> permissionToFineLocationResult.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getSelfLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val criteria = Criteria().apply { accuracy = Criteria.ACCURACY_COARSE }
            val provider = locationManager.getBestProvider(criteria, true)
            provider?.let {
                locationManager.requestLocationUpdates(
                    provider,
                    REFRESH_PERIOD,
                    MINIMAL_DISTANCE, onLocationListener
                )
            }
            doOnFineLocationPermissionGranted()
        } else {
            requestAccessToFineLocationWithDialog()
        }
    }

    private fun requestAccessToFineLocationWithDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.access_to_fine_location_title))
            .setMessage(getString(R.string.explanation_of_fine_location_permission))
            .setPositiveButton(getString(R.string.grant_access_button_text)) { _, _ ->
                permissionToFineLocationResult.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            .setNegativeButton(getString(R.string.negative_button_text)) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private val permissionToFineLocationResult: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            getSelfLocation()
        } else {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.access_to_fine_location_text))
                .setMessage(getString(R.string.explanation_of_fine_location_permission))
                .setNegativeButton(getString(R.string.close_button_text)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    companion object {
        private const val REFRESH_PERIOD = 60000L
        private const val MINIMAL_DISTANCE = 100f
    }
}
