package views.ringfortlist

import activities.RingfortView
import views.map.RingfortMapView
import main.MainApp
import models.RingfortModel
import models.UserModel
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import views.BasePresenter
import views.BaseView
import views.VIEW

class RingfortListPresenter(view: BaseView) : BasePresenter(view) {

    var current_user = UserModel()

    fun doAddRingfort() {
        view?.navigateTo(VIEW.RINGFORT)
    }

    fun doEditRingfort(ringfort: RingfortModel) {
        view?.navigateTo(VIEW.RINGFORT, 0, "ringfort_edit", ringfort)
    }

    fun doShowRingfortsMap() {
        view?.navigateTo(VIEW.MAPS)
    }

    fun loadRingforts() {
        view?.showRingforts(app.ringforts.findAll())
    }
}