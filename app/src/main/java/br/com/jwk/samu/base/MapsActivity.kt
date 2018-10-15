package br.com.jwk.samu.base

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import br.com.jwk.samu.R
import br.com.jwk.samu.data.model.Address
import br.com.jwk.samu.data.repository.dto.GeocodeDto
import br.com.jwk.samu.data.repository.remote.GoogleMapsService
import br.com.jwk.samu.extension.createDrawableVector
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.ext.android.inject
import retrofit2.Call


abstract class MapsActivity : AppCompatActivity() {

    private val googleApi by inject<GoogleMapsService>()

    private lateinit var mMap: GoogleMap
    private var lastCall: Call<GeocodeDto>? = null

    protected abstract val mapFragment: SupportMapFragment
    protected abstract val layoutId: Int
    protected abstract fun onMapClickListener(latLng: LatLng)
    protected abstract fun onLocationLoadedCallback(location: Location)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        mapFragment.getMapAsync(::onMapReady)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadMyLocation()
                }

                return
            }
        }
    }

    private fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setupMapCheckPermision()
    }

    private fun setupMapCheckPermision() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE), 1)
    }

    @SuppressLint("MissingPermission")
    fun loadMyLocation() {
        mMap.isMyLocationEnabled = true

        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = false

        mMap.setOnMapClickListener { latLng -> onMapClickListener(latLng) }

        val locationClient = LocationServices.getFusedLocationProviderClient(this)
        locationClient.lastLocation.addOnSuccessListener { location ->
            mMap.isMyLocationEnabled = false
            mMap.uiSettings.isMyLocationButtonEnabled = false

            onLocationLoadedCallback(location)
        }
    }

    protected fun moveCamera(latLng: LatLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
    }

    private fun isProviderEnabled(): Boolean {
        return (getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    protected fun clearMarkers() {
        if (mMap != null)
            mMap.clear()
    }

    protected fun addMarker(latLng: LatLng, markerTitle: String? = null, @DrawableRes markerIcon: Int = R.drawable.pin_samu) {
        mMap.addMarker(MarkerOptions().apply {
            position(latLng)
            title(markerTitle)
            createDrawableVector(this@MapsActivity, markerIcon)
        })
    }

    fun getAddress(latLng: LatLng, callback: ((Address?) -> Unit)) {
        lastCall?.cancel()
        lastCall = googleApi.getAddress(latLng, callback)
    }

    fun getAddress(nameAddress: String, callback: ((Address?) -> Unit)) {
        lastCall?.cancel()
        lastCall = googleApi.getAddress(nameAddress, callback)
    }
}