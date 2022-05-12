package com.example.myassignment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.myassignment.domain.model.Location
import com.example.myassignment.ui.MyNavHost
import com.example.myassignment.ui.theme.MyApp
import com.example.myassignment.util.showToast
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val imageUri: MutableState<Uri?> = mutableStateOf(null)

    private val currentLocation: MutableState<Location?> = mutableStateOf(null)

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val takePhoto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    imageUri.value = uri
                }
            }
        }

    private val requestPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it.keys.first() == Manifest.permission.ACCESS_COARSE_LOCATION ||
                it.keys.first() == Manifest.permission.ACCESS_FINE_LOCATION
            ) {
                if (it.all { entry -> entry.value }) {
                    getCurrentLocation()
                }
            } else {
                if (it.all { entry -> entry.value }) {
                    selectImage()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MyNavHost(
                    uri = imageUri,
                    currentLocation = currentLocation,
                    selectImage = selectImage,
                    getCurrentLocation = getCurrentLocation,
                    checkStoragePermission = checkStoragePermission,
                    checkLocationPermission = checkLocationPermission,
                    requestStoragePermissions = requestStoragePermissions,
                    requestLocationPermissions = requestLocationPermissions
                )
            }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private val selectImage: () -> Unit = {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        intent.flags = (Intent.FLAG_GRANT_READ_URI_PERMISSION
                or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        takePhoto.launch(intent)
    }


    @SuppressLint("MissingPermission")
    private val getCurrentLocation: () -> Unit = {
        if (isLocationEnabled()) {

            val locationRequest = LocationRequest.create().apply {
                interval = 60000
                fastestInterval = 5000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()!!
            )

            fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                if (location != null) {
                    val geocoder = Geocoder(this, Locale.getDefault())
                    val list: List<Address> =
                        geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    currentLocation.value =
                        Location(list[0].latitude, list[0].longitude, list[0].getAddressLine(0))
                }
            }
        } else {
            showToast("Please Turn On Location")
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }


    private val locationCallback: LocationCallback = object : LocationCallback() {}

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


    private val checkStoragePermission: () -> Boolean = {
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        permissions.all {
            checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private val requestStoragePermissions: () -> Unit = {
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (!checkStoragePermission()) {
            requestPermissions.launch(permissions)
        }
    }

    private val checkLocationPermission: () -> Boolean = {
        var permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            permissions += listOf(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        permissions.all {
            checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
        }
    }


    private val requestLocationPermissions: () -> Unit = {
        var permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            permissions += listOf(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if (!checkLocationPermission()) {
            requestPermissions.launch(permissions)
        }
    }

}