package views.ringfortlist

import views.login.LoginView
import views.settings.SettingsView
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_ringfort_list.*
import org.wit.ringfort.R
import models.RingfortModel
import org.jetbrains.anko.*
import views.BaseView

class RingfortListView :  BaseView(), RingfortListener {

    lateinit var presenter: RingfortListPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ringfort_list)
        toolbar.title = title
        setSupportActionBar(toolbar)

        presenter = RingfortListPresenter(this)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        presenter.loadRingforts()
    }

    override fun showRingforts(ringforts: List<RingfortModel>) {
        recyclerView.adapter = RingfortAdapter(ringforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> {
                presenter.doAddRingfort()
            }
            R.id.item_map -> {
                presenter.doShowRingfortsMap()
            }
            R.id.item_logout -> {
                //app.loginUser = UserModel()
                toast("Logged out")
                startActivityForResult<LoginView>(0)
                finish()
            }
            R.id.item_settings -> {
                //app.loginUser = UserModel()
                toast("Viewing Settings")
                startActivityForResult<SettingsView>(0)
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRingfortClick(ringfort: RingfortModel) {
        presenter.doEditRingfort(ringfort)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.loadRingforts()
        super.onActivityResult(requestCode, resultCode, data)
    }
}