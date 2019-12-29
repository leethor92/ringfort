package views.map

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import helpers.readImageFromPath

import kotlinx.android.synthetic.main.activity_ringfort_maps.*
import kotlinx.android.synthetic.main.content_ringfort_maps.*
import main.MainApp
import models.RingfortModel
import org.wit.ringfort.R
import views.BaseView

class RingfortMapView :  BaseView(), GoogleMap.OnMarkerClickListener {

    lateinit var presenter: RingfortMapPresenter
    lateinit var map : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ringfort_maps)
        super.init(toolbar, true)

        presenter = initPresenter (RingfortMapPresenter(this)) as RingfortMapPresenter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync {
            map = it
            map.setOnMarkerClickListener(this)
            presenter.loadRingforts()
        }
    }

    override fun showRingfort(ringfort: RingfortModel) {
        currentTitle.text = ringfort.title
        currentDescription.text = ringfort.description
        currentImage.setImageBitmap(readImageFromPath(this, ringfort.images[0]))
    }

    override fun showRingforts(ringforts: List<RingfortModel>) {
        presenter.doPopulateMap(map, ringforts)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doMarkerSelected(marker)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
    
    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
