package org.wit.placemark.activities

import activities.MapActivity
import activities.RingfortActivity
import android.content.Intent
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import helpers.readImageFromPath
import helpers.showImagePicker
import kotlinx.android.synthetic.main.activity_ringfort.*
import main.MainApp
import models.Location
import models.RingfortModel
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.wit.ringfort.R
import java.text.SimpleDateFormat
import java.util.*

class RingfortPresenter(val view: RingfortActivity) {

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    var ringfort = RingfortModel()
    var location = Location(52.245696, -7.139102, 15f)
    var app: MainApp
    var edit = false;
    var formatD = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val currentDate = formatD.format(Date())

    init {
        app = view.application as MainApp
        if (view.intent.hasExtra("ringfort_edit")) {
            edit = true
            ringfort = view.intent.extras?.getParcelable<RingfortModel>("ringfort_edit")!!
            view.showRingfort(ringfort)
        }
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
        view.finish()
    }



    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        app.ringforts.delete(ringfort)
        view.finish()
    }

    fun doSelectImage() {
        showImagePicker(view, IMAGE_REQUEST)
    }

    fun doSetLocation() {
        if (ringfort.zoom != 0f) {
            location.lat = ringfort.lat
            location.lng = ringfort.lng
            location.zoom = ringfort.zoom
        }
        view.startActivityForResult(view.intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
    }

    fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    ringfort.images.add(data.getData().toString())
                    //view.showRingfort(ringfort)
                }
            }
            LOCATION_REQUEST -> {
                location = data.extras?.getParcelable<Location>("location")!!
                ringfort.lat = location.lat
                ringfort.lng = location.lng
                ringfort.zoom = location.zoom
            }
        }
    }
}