package org.wit.placemark.activities

import activities.LoginActivity
import activities.RingfortActivity
import activities.RingfortListActivity
import activities.RingfortMapsActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import main.MainApp
import models.RingfortModel
import models.UserModel
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

class RingfortListPresenter(val view: RingfortListActivity) {

    var app: MainApp

    var current_user = UserModel()

    init {
        app = view.application as MainApp

    }

    fun getRingforts() = app.ringforts.findAll()

    fun doAddRingfort() {
        view.startActivityForResult<RingfortActivity>(0)
    }

    fun doEditRingfort(ringfort: RingfortModel) {
        view.startActivityForResult(view.intentFor<RingfortActivity>().putExtra("ringfort_edit", ringfort), 0)
    }


    fun doShowRingfortsMap() {
        view.startActivity<RingfortMapsActivity>()
    }
}