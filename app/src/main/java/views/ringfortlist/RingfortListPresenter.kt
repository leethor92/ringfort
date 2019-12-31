package views.ringfortlist

import activities.RingfortView
import com.google.firebase.auth.FirebaseAuth
import views.map.RingfortMapView
import main.MainApp
import models.RingfortModel
import models.UserModel
import org.jetbrains.anko.*
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
        doAsync {
            val ringforts = app.ringforts.findAll()
            uiThread {
                view?.showRingforts(ringforts)
            }
        }
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        view?.navigateTo(VIEW.LOGIN)
    }

    fun doSettings() {
        view?.navigateTo(VIEW.SETTINGS)
    }

}