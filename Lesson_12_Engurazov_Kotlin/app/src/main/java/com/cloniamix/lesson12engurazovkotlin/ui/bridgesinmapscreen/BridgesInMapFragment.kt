package com.cloniamix.lesson12engurazovkotlin.ui.bridgesinmapscreen

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
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.cloniamix.lesson12engurazovkotlin.BuildConfig
import com.cloniamix.lesson12engurazovkotlin.MyApplication.Companion.APP_TAG
import com.cloniamix.lesson12engurazovkotlin.R
import com.cloniamix.lesson12engurazovkotlin.data.model.Bridge
import com.cloniamix.lesson12engurazovkotlin.di.ApplicationComponents
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.bridges_map_fragment.*
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.view_bridge_item.view.*

class BridgesInMapFragment :
    Fragment(),
    OnMapReadyCallback,
    BridgesInMapMvpView {

    companion object {

        private const val ZOOM_MIN = 4.0f
        private const val ZOOM_MAX = 20.0f
        private const val ZOOM_CAMERA = 10.0f

        private const val PETERSBURG_LAT = 59.9199
        private const val PETERSBURG_LNG = 30.33529

        private const val REQUEST_CODE_PERMISSION = 101
        private const val REQUEST_CHECK_SETTINGS = 0x1

        fun newInstance(): BridgesInMapFragment {
            return BridgesInMapFragment()
        }
    }

    private var listener: OnBridgesInMapFragmentInteractionListener? = null
    private val bridgesInMapFragmentPresenter: BridgesInMapFragmentPresenter =
        ApplicationComponents.getInstance().provideBridgesInMapFragmentPresenter()

    private var map: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var settingsClient: SettingsClient

    private var requestingLocationUpdates = false

    private lateinit var locationCallback: LocationCallback

    private var currentLocation: Location? = null
    private var myLocationMarker: Marker? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBridgesInMapFragmentInteractionListener) {
            listener = context

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            settingsClient = LocationServices.getSettingsClient(context)

        } else {
            throw RuntimeException("$context must implement OnBridgesInMapFragmentInteractionListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bridges_map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        bridgesInMapFragmentPresenter.attachView(this)
        bridgesInMapFragmentPresenter.onViewCreated()
    }

    private fun init() {
        textViewError.setOnClickListener { bridgesInMapFragmentPresenter.onRefresh() }

        toolbarMap.inflateMenu(R.menu.list_menu)
        toolbarMap.setOnMenuItemClickListener {
            if (it.itemId == R.id.itemList) {
                listener?.menuListItemClicked()
            }
            true
        }

        val fragmentMap: SupportMapFragment =
            childFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment

        fragmentMap.getMapAsync(this)
    }

    override fun onDetach() {
        listener = null
        bridgesInMapFragmentPresenter.detachView()
        super.onDetach()
    }


    override fun showState(stateFlag: Int) {
        viewFlipperMap.displayedChild = stateFlag
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkPermissions()) {
                requestPermissions()
            }
        }
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

        settingsClient.checkLocationSettings(
            bridgesInMapFragmentPresenter.buildLocationSettingsRequest()
        )
            .addOnSuccessListener {
                Log.d(APP_TAG, "All location settings are satisfied.")
                fusedLocationClient.requestLocationUpdates(
                    bridgesInMapFragmentPresenter.createLocationRequest(),
                    locationCallback,
                    Looper.myLooper()
                )

                updateLocationUI()
            }
            .addOnFailureListener {
                val statusCode = (it as ApiException).statusCode
                when (statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        Log.d(
                            APP_TAG,
                            "Location settings are not satisfied. Attempting to upgrade " + "location settings "
                        )
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the
                            // result in onActivityResult().
                            val rae = it as ResolvableApiException
                            rae.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS)
                        } catch (e: IntentSender.SendIntentException) {
                            Log.d(APP_TAG, "PendingIntent unable to execute request.")
                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        val errorMessage = "Location settings are inadequate, and cannot be " +
                                "fixed here. Fix in Settings."
                        Log.e(APP_TAG, errorMessage)
                        Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
                        requestingLocationUpdates = false
                    }
                }

                updateLocationUI()
            }

    }

    private fun stopLocationUpdates() {
        if (!requestingLocationUpdates) {
            Log.d(APP_TAG, "stopLocationUpdates: updates never requested, no-op.")
            return
        }
        Log.d(APP_TAG, "stopLocationUpdates: location updates stopped.")
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
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {

        this.map = googleMap

        val saintPetersburg = LatLng(PETERSBURG_LAT, PETERSBURG_LNG)
        this.map?.let {
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(saintPetersburg, ZOOM_CAMERA))
            it.setMinZoomPreference(ZOOM_MIN)
            it.setMaxZoomPreference(ZOOM_MAX)
            it.uiSettings.isZoomControlsEnabled = true
            it.uiSettings.isZoomGesturesEnabled = true

            it.setOnMarkerClickListener { marker ->
                showBridgeInfoByMarker(marker)
                false
            }

            it.setOnMapClickListener { viewBridgeItem.visibility = View.GONE }
        }

        if (checkPermissions()) {
            if (!requestingLocationUpdates) {
                requestingLocationUpdates = true
                startLocationUpdates()
            }
        }

        createLocationCallback()
    }

    override fun addMarkers(bridges: List<Bridge>) {
        for (bridge in bridges) {
            val markerOptions = MarkerOptions()
                .position(LatLng(bridge.lat, bridge.lng))
                .title(bridge.name)
                .icon(BitmapDescriptorFactory.fromResource(bridgesInMapFragmentPresenter.getStatusIconResId(bridge)))
            val marker: Marker? = map?.addMarker(markerOptions)
            marker?.tag = bridges.indexOf(bridge)
        }
    }

    private fun checkPermissions(): Boolean {

        val permissionState = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED

    }

    private fun requestPermissions() {

        val shouldProvideRationale =
            ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)

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
            activity!!,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE_PERMISSION
        )
    }

    private fun showBridgeInfoByMarker(marker: Marker) {
        if (marker.tag != null) {
            val bridge = bridgesInMapFragmentPresenter.getBridges()[marker.tag as Int]

            viewBridgeItem.visibility = View.VISIBLE

            viewBridgeItem.setOnClickListener { listener?.bridgeItemMapClicked(bridge.id) }
            viewBridgeItem.textViewBridgeName.text = bridge.name
            viewBridgeItem.textViewTime.text = bridgesInMapFragmentPresenter.getDivorceTime(bridge)
            viewBridgeItem.imageViewStatusIcon.setImageResource(bridgesInMapFragmentPresenter.getStatusIconResId(bridge))
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
            viewBridgeItem,
            getString(mainTextStringId)
            , Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setAction(getString(actionStringId), listener).show()
    }


    interface OnBridgesInMapFragmentInteractionListener {
        fun menuListItemClicked()
        fun bridgeItemMapClicked(bridgeId: Int)
    }
}