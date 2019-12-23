package views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import helpers.readImageFromPath
import kotlinx.android.synthetic.main.content_ringfort_maps.*
import main.MainApp

class RingfortMapPresenter(val view: RingfortMapView){

    var app: MainApp

    init{
        app = view.application as MainApp
    }

    fun doPopulateMap(map: GoogleMap){
        map.uiSettings.setZoomControlsEnabled(true)
        map.setOnMarkerClickListener(view)
        app.ringforts.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    fun doMarkerSelected(marker: Marker){
        val tag = marker.tag as Long
        val ringfort = app.ringforts.findById(tag)
        view.currentTitle.text = ringfort!!.title
        view.currentDescription.text = ringfort!!.description
        if (ringfort.images.size > 0) {
            view.currentImage.setImageBitmap(readImageFromPath(view.applicationContext, ringfort.images[0]))
        }
    }
}