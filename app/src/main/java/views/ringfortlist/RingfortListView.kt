package views.ringfortlist

import activities.LoginView
import activities.SettingsView
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_ringfort_list.*
import org.wit.ringfort.R
import models.RingfortModel
import org.jetbrains.anko.*

class RingfortListView : AppCompatActivity(), RingfortListener {

    lateinit var presenter: RingfortListPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ringfort_list)
        toolbar.title = title
        setSupportActionBar(toolbar)

        presenter = RingfortListPresenter(this)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter =
            RingfortAdapter(presenter.getRingforts(), this)
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
        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}