package com.cloniamix.lesson10engurazovkotlin.screens.screenbridgeinmap

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.cloniamix.lesson10engurazovkotlin.BuildConfig
import com.cloniamix.lesson10engurazovkotlin.R
import com.cloniamix.lesson10engurazovkotlin.network.BridgeApiService
import com.cloniamix.lesson10engurazovkotlin.pojo.Bridge
import com.cloniamix.lesson10engurazovkotlin.pojo.Bridges
import com.cloniamix.lesson10engurazovkotlin.screens.screenbridgedetails.BridgeDetailsActivity
import com.cloniamix.lesson10engurazovkotlin.screens.screenbridgelist.MainActivity
import com.cloniamix.lesson10engurazovkotlin.utils.Utils
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_map.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.view_bridge_item.view.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private const val FLAG_PROGRESS = 0
        private const val FLAG_ERROR = 1
        private const val FLAG_MAP = 2

        private const val ZOOM_MIN = 4.0f
        private const val ZOOM_MAX = 20.0f
        private const val ZOOM_CAMERA = 10.0f

        private const val PETERSBURG_LAT = 59.9199
        private const val PETERSBURG_LNG = 30.33529

        private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS: Long = UPDATE_INTERVAL_IN_MILLISECONDS / 2

        private const val REQUEST_CODE_PERMISSION = 101
        private const val REQUEST_CHECK_SETTINGS = 0x1

        fun createStartIntent(context: Context): Intent {
            return Intent(context, MapActivity::class.java)
        }
    }

    private var map: GoogleMap? = null
    private var disposable: Disposable? = null
    private lateinit var bridges: List<Bridge>

    private var requestingLocationUpdates = false
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var settingsClient: SettingsClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationSettingsRequest: LocationSettingsRequest
    private var currentLocation: Location? = null
    private var myLocationMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.cloniamix.lesson10engurazovkotlin.R.layout.activity_map)

        showView(FLAG_PROGRESS)
        init()
        getBridgeListFromApi()

    }

    private fun init() {
        imageViewRefreshMap.setOnClickListener { refreshUi() }

        toolbarMap.inflateMenu(R.menu.list_menu)
        toolbarMap.setOnMenuItemClickListener {
            if (it.itemId == R.id.itemList) {
                startActivity(MainActivity.createStartIntent(this).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
            }
            true
        }

        val fragmentMap: SupportMapFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        fragmentMap.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        settingsClient = LocationServices.getSettingsClient(this)

        createLocationRequest()
        buildLocationSettingsRequest()

    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()

        locationRequest.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                currentLocation = locationResult.lastLocation
                updateLocationUI()
            }
        }
    }

    private fun buildLocationSettingsRequest() {
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)
        locationSettingsRequest = builder.build()
    }


    override fun onResume() {
        super.onResume()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkPermissions()) {
                requestPermissions()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        startLocationUpdates()
                    }
                    Activity.RESULT_CANCELED -> {
                        requestingLocationUpdates = false
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        //map?.isMyLocationEnabled = true

        settingsClient.checkLocationSettings(locationSettingsRequest)
            .addOnSuccessListener {
                Utils.log("All location settings are satisfied.")
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

                updateLocationUI()
            }
            .addOnFailureListener {
                val statusCode = (it as ApiException).statusCode
                when (statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        Utils.log("Location settings are not satisfied. Attempting to upgrade " + "location settings ")
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the
                            // result in onActivityResult().
                            val rae = it as ResolvableApiException
                            rae.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                        } catch (e: IntentSender.SendIntentException) {
                            Utils.log("PendingIntent unable to execute request.")
                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        val errorMessage = "Location settings are inadequate, and cannot be " +
                                "fixed here. Fix in Settings."
                        Utils.log(errorMessage)
                        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                        requestingLocationUpdates = false
                    }
                }

                updateLocationUI()
            }

    }

    private fun stopLocationUpdates() {
        if (!requestingLocationUpdates) {
            Utils.log("stopLocationUpdates: updates never requested, no-op.")
            return
        }
        fusedLocationClient.removeLocationUpdates(locationCallback)
            .addOnCompleteListener { requestingLocationUpdates = false }
    }

    private fun updateLocationUI() {
        if (currentLocation != null) {
            currentLocation.let {
                if (myLocationMarker == null) {
                    myLocationMarker = map?.addMarker(
                        MarkerOptions()
                            .position(LatLng(currentLocation!!.latitude, currentLocation!!.longitude))
                            .anchor(0.5.toFloat(), 0.5.toFloat())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_png))
                    )
                } else {
                    myLocationMarker?.position = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
                }
            }
            Utils.log("Lat: ${currentLocation?.latitude}; Lon: ${currentLocation?.longitude}")

        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {

        this.map = googleMap

        val saintPetersburg = LatLng(PETERSBURG_LAT, PETERSBURG_LNG)
        this.map?.moveCamera(CameraUpdateFactory.newLatLngZoom(saintPetersburg, ZOOM_CAMERA))
        this.map?.setMinZoomPreference(ZOOM_MIN)
        this.map?.setMaxZoomPreference(ZOOM_MAX)
        this.map?.uiSettings?.isZoomControlsEnabled = true
        this.map?.uiSettings?.isZoomGesturesEnabled = true

        if (checkPermissions()) {
            if (!requestingLocationUpdates) {
                requestingLocationUpdates = true
                startLocationUpdates()
            }
        }
        createLocationCallback()

        /*map?.setOnMyLocationButtonClickListener {
            if (!requestingLocationUpdates) {
                requestingLocationUpdates = true
                startLocationUpdates()
            }

            false
        }*/
        this.map?.setOnMarkerClickListener { marker ->
            showBridgeInfoByMarker(marker)
            false
        }

        this.map?.setOnMapClickListener { view.visibility = View.GONE }


    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    private fun getBridgeListFromApi() {
        disposable = BridgeApiService.getClient.getBridges()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::addBridgeMarkers, this::onError)
    }

    private fun addBridgeMarkers(bridgesObject: Bridges) {
        showView(FLAG_MAP)
        bridges = bridgesObject.objects
        addMarkers(bridges)
    }

    private fun addMarkers(bridges: List<Bridge>?) {
        if (bridges != null) {
            var count = 0
            for (bridge in bridges) {
                val markerOptions = MarkerOptions()
                    .position(LatLng(bridge.lat, bridge.lng))
                    .title(bridge.name)
                    .icon(BitmapDescriptorFactory.fromResource(setStatusIconResId(Utils.getBridgeStatus(bridge.divorces))))
                val marker: Marker? = map?.addMarker(markerOptions)
                marker?.tag = count
                count++
            }
        }

    }

    private fun onError(t: Throwable?) {
        showView(FLAG_ERROR)
        Utils.log(t.toString())

    }


    private fun checkPermissions(): Boolean {

        val permissionState = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED

    }

    private fun requestPermissions() {

        val shouldProvideRationale =
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)

        if (shouldProvideRationale) {
            showSnackbar(
                R.string.snackbar_permissions_text,
                android.R.string.ok,
                View.OnClickListener { permissionsRequestBody() })
        } else {
            permissionsRequestBody()
        }
    }

    private fun permissionsRequestBody() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE_PERMISSION
        )
    }

    private fun showBridgeInfoByMarker(marker: Marker) {
        if (marker.tag != null) {
            val bridge = bridges[marker.tag as Int]

            view.visibility = View.VISIBLE

            view.setOnClickListener { startActivity(BridgeDetailsActivity.createStartIntent(this, bridge)) }
            view.textViewBridgeName.text = bridge.name
            view.textViewTime.text = Utils.getStringDivorceTime(bridge.divorces)
            view.imageViewStatusIcon.setImageResource(setStatusIconResId(Utils.getBridgeStatus(bridge.divorces)))
        }
    }


    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION ->
                if (permissions.isNotEmpty() &&
                    permissions[0] === Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {

                    /*map?.isMyLocationEnabled = true*/
                    startLocationUpdates()
                }
            else -> {
                showSnackbar(R.string.permission_denied_explanation,
                    R.string.settings, View.OnClickListener {

                        // Build intent that displays the App settings screen.
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                        intent.data = uri
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    })
            }
        }
    }

    private fun showSnackbar(mainTextStringId: Int, actionStringId: Int, listener: View.OnClickListener) {
        val snackbar = Snackbar.make(
            view,
            getString(mainTextStringId)
            , Snackbar.LENGTH_INDEFINITE
        )

        snackbar.setAction(getString(actionStringId), listener).show()

    }


    private fun refreshUi() {
        showView(FLAG_PROGRESS)
        getBridgeListFromApi()
    }

    private fun showView(flag: Int) {
        viewFlipperMap.displayedChild = flag
    }

    private fun setStatusIconResId(status: Int): Int {
        when (status) {
            Utils.STATUS_SOON -> {
                return R.drawable.ic_brige_soon_png
            }
            Utils.STATUS_LATE -> {
                return R.drawable.ic_brige_late_png
            }
        }
        return R.drawable.ic_brige_normal_png
    }
}
