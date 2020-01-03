package views.navigator

import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.model.TravelMode
import kotlinx.android.synthetic.main.activity_navigator.*
import org.wit.ringfort.R
import views.BaseView

class NavigatorView : BaseView() {

  private lateinit var presenter: NavigatorPresenter
  private lateinit var map: GoogleMap

  private var myLocation: LatLng = LatLng(0.0, 0.0)


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_navigator)

    presenter = initPresenter(NavigatorPresenter(this)) as NavigatorPresenter

    navigatorMap.onCreate(savedInstanceState)
    navigatorMap.getMapAsync {
      map = it
      myLocation = presenter.getCurrentLocation()
      presenter.populateMap(map, myLocation, TravelMode.DRIVING)

    }

    drivingRadioButton.isChecked = true

    walkingRadioButton.setOnClickListener {
      walkingRadioButton.isChecked = true
      map.clear()
      presenter.populateMap(map, myLocation, TravelMode.WALKING)
    }

    drivingRadioButton.setOnClickListener {
      drivingRadioButton.isChecked = true
      map.clear()
      presenter.populateMap(map, myLocation, TravelMode.DRIVING)
    }
  }


  override fun onDestroy() {
    super.onDestroy()
    navigatorMap.onDestroy()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    navigatorMap.onLowMemory()
  }

  override fun onPause() {
    super.onPause()
    navigatorMap.onPause()
  }

  override fun onResume() {
    super.onResume()
    navigatorMap.onResume()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    navigatorMap.onSaveInstanceState(outState)
  }
}