package org.wit.placemark.activities

import views.editlocation.EditLocationView
import activities.RingfortView
import android.content.Intent
import helpers.showImagePicker
import main.MainApp
import models.Location
import models.RingfortModel
import org.jetbrains.anko.intentFor
import views.*
import java.text.SimpleDateFormat
import java.util.*

class RingfortPresenter(view: BaseView) : BasePresenter(view) {

    var ringfort = RingfortModel()
    var default_location = Location(52.245696, -7.139102, 15f)
    var edit = false;
    var formatD = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val currentDate = formatD.format(Date())

    init {
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
        if (edit == false) {
            view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", default_location)
        } else {
            view?.navigateTo(
                VIEW.LOCATION,
                LOCATION_REQUEST,
                "location",
                Location(ringfort.lat, ringfort.lng, ringfort.zoom)
            )
        }
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
            }
        }
    }
}