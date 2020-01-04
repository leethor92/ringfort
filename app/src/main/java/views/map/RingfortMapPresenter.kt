package views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import helpers.readImageFromPath
import kotlinx.android.synthetic.main.content_ringfort_maps.*
import main.MainApp
import models.RingfortModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import views.BasePresenter
import views.BaseView

class RingfortMapPresenter(view: BaseView) : BasePresenter(view){

    fun doPopulateMap(map: GoogleMap, ringforts: List<RingfortModel>){
        map.uiSettings.setZoomControlsEnabled(true)
        ringforts.forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    fun doMarkerSelected(marker: Marker){
        doAsync {
            val ringfort = marker.tag as RingfortModel
            uiThread {
                if (ringfort != null) view?.showRingfort(ringfort)
            }
        }
    }

    fun loadRingforts() {
        doAsync {
            val ringforts = app.ringforts.findAll()
            uiThread {
                view?.showRingforts(ringforts)
            }
        }
    }
}