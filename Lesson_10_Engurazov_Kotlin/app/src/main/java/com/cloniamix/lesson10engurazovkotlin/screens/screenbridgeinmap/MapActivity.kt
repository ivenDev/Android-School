package com.cloniamix.lesson10engurazovkotlin.screens.screenbridgeinmap

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cloniamix.lesson10engurazovkotlin.R
import com.cloniamix.lesson10engurazovkotlin.network.BridgeApiService
import com.cloniamix.lesson10engurazovkotlin.pojo.Bridge
import com.cloniamix.lesson10engurazovkotlin.pojo.Bridges
import com.cloniamix.lesson10engurazovkotlin.screens.screenbridgedetails.BridgeDetailsActivity
import com.cloniamix.lesson10engurazovkotlin.screens.screenbridgelist.MainActivity
import com.cloniamix.lesson10engurazovkotlin.utils.Utils
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
        private const val FLAG_MAP = 1
        private const val FLAG_ERROR = 2
        private const val FLAG_PROGRESS = 3

        private const val REQUEST_CODE_PERMISSION = 101

        fun createStartIntent(context: Context): Intent {
            return Intent(context, MapActivity::class.java)
        }
    }

    private var map: GoogleMap? = null
    private var disposable: Disposable? = null
    private lateinit var bridges: List<Bridge>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.cloniamix.lesson10engurazovkotlin.R.layout.activity_map)

        imageViewRefreshMap.setOnClickListener { refreshUi() }

        toolbarMap.inflateMenu(com.cloniamix.lesson10engurazovkotlin.R.menu.list_menu)
        toolbarMap.setOnMenuItemClickListener {
            if (it.itemId == com.cloniamix.lesson10engurazovkotlin.R.id.itemList) {
                startActivity(MainActivity.createStartIntent(this).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
            }
            true
        }

        val fragmentMap: SupportMapFragment =
            supportFragmentManager.findFragmentById(com.cloniamix.lesson10engurazovkotlin.R.id.fragmentMap) as SupportMapFragment
        fragmentMap.getMapAsync(this)
        showView(FLAG_PROGRESS)
        getBridgeListFromApi()

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    private fun getBridgeListFromApi() {
        disposable = BridgeApiService.getClient.getBridges()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::updateUi, this::onError)
    }

    private fun updateUi(bridgesObject: Bridges) {
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

    override fun onMapReady(p0: GoogleMap?) {
        map = p0

        val saintPetersburg = LatLng(59.9199, 30.33529)
        map?.addMarker(
            MarkerOptions()
                .position(saintPetersburg)
                .title("Санкт-Петербург")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_png))
        )
        map?.moveCamera(CameraUpdateFactory.newLatLng(saintPetersburg))
        map?.setMinZoomPreference(4.0f)
        map?.setMaxZoomPreference(20.0f)
        map?.uiSettings?.isZoomControlsEnabled = true
        map?.uiSettings?.isZoomGesturesEnabled = true

        map?.setOnMarkerClickListener { marker ->
            showBridgeInfoByMarker(marker)
            false
        }

        map?.setOnMapClickListener { view.visibility = View.GONE }

        checkPermissions()

    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                map?.isMyLocationEnabled = true
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showPermissionsSnackbar()
                } else {
                    setPermissionsRequest()
                }
            }
        }
    }

    private fun showBridgeInfoByMarker(marker: Marker) {
        val bridge = bridges[marker.tag as Int]

        view.visibility = View.VISIBLE

        view.setOnClickListener { startActivity(BridgeDetailsActivity.createStartIntent(this, bridge)) }
        view.textViewBridgeName.text = bridge.name
        view.textViewTime.text = Utils.getStringDivorceTime(bridge.divorces)
        view.imageViewStatusIcon.setImageResource(setStatusIconResId(Utils.getBridgeStatus(bridge.divorces)))
    }

    //fixme: получить местоположение сразу после получения permissions
    /*override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                map?.isMyLocationEnabled = true
            }
        }
    }*/

    private fun showPermissionsSnackbar() {
        val snackbar = Snackbar.make(view, getString(R.string.snackbar_permissions_text), Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("Ok") {
            snackbar.dismiss()
            setPermissionsRequest()
        }
            .show()
    }

    private fun setPermissionsRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE_PERMISSION
        )
    }


    private fun refreshUi() {
        showView(FLAG_PROGRESS)
        getBridgeListFromApi()
    }

    private fun showView(flag: Int) {

        when (flag) {
            FLAG_MAP -> {
                Utils.setViewVisible(progressBarMap, false)
                Utils.setViewVisible(linearLayoutMapContainer, true)
                Utils.setViewVisible(layoutErrorMap, false)
            }

            FLAG_ERROR -> {
                Utils.setViewVisible(progressBarMap, false)
                Utils.setViewVisible(linearLayoutMapContainer, false)
                Utils.setViewVisible(layoutErrorMap, true)
            }

            FLAG_PROGRESS -> {
                Utils.setViewVisible(progressBarMap, true)
                Utils.setViewVisible(linearLayoutMapContainer, false)
                Utils.setViewVisible(layoutErrorMap, false)
            }
        }
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
