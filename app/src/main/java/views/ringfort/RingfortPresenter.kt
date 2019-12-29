package org.wit.placemark.activities

import views.editlocation.EditLocationView
import activities.RingfortView
import android.annotation.SuppressLint
import android.content.Intent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import helpers.checkLocationPermissions
import helpers.createDefaultLocationRequest
import helpers.isPermissionGranted
import helpers.showImagePicker
import main.MainApp
import models.Location
import models.RingfortModel
import org.jetbrains.anko.intentFor
import views.*
import java.text.SimpleDateFormat
import java.util.*

class RingfortPresenter(view: BaseView) : BasePresenter(view) {

    var map: GoogleMap? = null
    var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)

    var ringfort = RingfortModel()
    var location = Location(52.245696, -7.139102, 15f)
    var edit = false;
    var formatD = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val currentDate = formatD.format(Date())
    val locationRequest = createDefaultLocationRequest()

    init {
        if (view.intent.hasExtra("ringfort_edit")) {
            edit = true
            ringfort = view.intent.extras?.getParcelable<RingfortModel>("ringfort_edit")!!
            view.showRingfort(ringfort)
        } else {
            if (checkLocationPermissions(view)) {
                doSetCurrentLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {
        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(it.latitude, it.longitude)
        }
    }

    override fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (isPermissionGranted(requestCode, grantResults)) {
            doSetCurrentLocation()
        } else {
            // permissions denied, so use the default location
            locationUpdate(location.lat, location.lng)
        }
    }

    fun doConfigureMap(m: GoogleMap) {
        map = m
        locationUpdate(ringfort.lat, ringfort.lng)
    }

    fun locationUpdate(lat: Double, lng: Double) {
        ringfort.lat = lat
        ringfort.lng = lng
        ringfort.zoom = 15f
        map?.clear()
        map?.uiSettings?.setZoomControlsEnabled(true)
        val options = MarkerOptions().title(ringfort.title).position(LatLng(ringfort.lat, ringfort.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(ringfort.lat, ringfort.lng), ringfort.zoom))
        view?.showRingfort(ringfort)
    }

    fun doAddOrSave(title: String, description: String, checkBox: Boolean, addNotes: String, date: String) {
        ringfort.title = title
        ringfort.description = description
        ringfort.notes = addNotes
        ringfort.visited = checkBox

        if (ringfort.visited == true) {
            ringfort.date = currentDate
        }
        else
        {
            ringfort.date = "Not Visited yet"
        }

        if (edit) {
            app.ringforts.update(ringfort)
        } else {
            app.ringforts.create(ringfort)
        }
        view?.finish()
    }



    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        app.ringforts.delete(ringfort)
        view?.finish()
    }

    fun doSelectImage() {
        showImagePicker(view!!, IMAGE_REQUEST)
    }

    fun doSetLocation() {
        view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", Location(ringfort.lat, ringfort.lng, ringfort.zoom))
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    ringfort.images.add(data.getData().toString())
                    //view.showRingfort(ringfort)
                }
            }
            LOCATION_REQUEST -> {
                val location = data.extras?.getParcelable<Location>("location")!!
                ringfort.lat = location.lat
                ringfort.lng = location.lng
                ringfort.zoom = location.zoom
                locationUpdate(ringfort.lat, ringfort.lng)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun doResartLocationUpdates() {
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations != null) {
                    val l = locationResult.locations.last()
                    locationUpdate(l.latitude, l.longitude)
                }
            }
        }
        if (!edit) {
            locationService.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }
}