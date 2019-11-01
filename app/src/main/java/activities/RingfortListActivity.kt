package activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_ringfort_list.*
import kotlinx.android.synthetic.main.card_ringfort.view.*
import org.wit.ringfort.R
import main.MainApp
import models.RingfortModel
import models.UserModel
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

class RingfortListActivity : AppCompatActivity(), RingfortListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ringfort_list)
        app = application as MainApp

        toolbar.title = title
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadRingforts()

        toolbar.setOnClickListener{

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> {
                startActivityForResult<RingfortActivity>(0)
            }
            R.id.item_logout -> {
                app.loginUser = UserModel()
                toast("Logged out")
                startActivityForResult<LoginActivity>(0)
                finish()
            }
            R.id.item_settings -> {
                app.loginUser = UserModel()
                toast("Viewing Settings")
                startActivityForResult<SettingsActivity>(0)
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRingfortClick(ringfort: RingfortModel) {
        startActivityForResult(intentFor<RingfortActivity>().putExtra("ringfort_edit", ringfort), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadRingforts()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadRingforts() {
        showRingforts(app.ringforts.findAll())
    }

    fun showRingforts (ringforts: List<RingfortModel>) {
        recyclerView.adapter = RingfortAdapter(ringforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}