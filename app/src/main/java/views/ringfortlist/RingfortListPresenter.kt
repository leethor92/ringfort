package views.ringfortlist

import activities.RingfortView
import views.map.RingfortMapView
import main.MainApp
import models.RingfortModel
import models.UserModel
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

class RingfortListPresenter(val view: RingfortListView) {

    var app: MainApp

    var current_user = UserModel()

    init {
        app = view.application as MainApp

    }

    fun getRingforts() = app.ringforts.findAll()

    fun doAddRingfort() {
        view.startActivityForResult<RingfortView>(0)
    }

    fun doEditRingfort(ringfort: RingfortModel) {
        view.startActivityForResult(view.intentFor<RingfortView>().putExtra("ringfort_edit", ringfort), 0)
    }


    fun doShowRingfortsMap() {
        view.startActivity<RingfortMapView>()
    }
}