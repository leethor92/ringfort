package views.settings

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_settings.*
import main.MainApp
import models.RingfortModel
import models.UserModel
import org.jetbrains.anko.*
import org.wit.ringfort.R
import views.ringfortlist.RingfortListView

class SettingsView : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp

    var current_user = FirebaseAuth.getInstance().currentUser
    var ringfort = RingfortModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        app = application as MainApp

        doAsync {
            var size = app.ringforts.findAll().size
            var count = 0

            for (r in app.ringforts.findAll()) {
                if (r.visited) {
                    count++
                }
            }
            uiThread {
                totalRingforts.setText("Number of Ringforts: " + size)
                visitedRingforts.setText("Amount visited: " + count)
            }
        }

        save_settings.setOnClickListener {
            if (settings_password.text.isNotEmpty()) {
                val currentUser = FirebaseAuth.getInstance().currentUser
                currentUser?.updatePassword(settings_password.text.toString())
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                app.applicationContext,
                                "User Password Updated",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                app.applicationContext,
                                "Please Enter a Valid Password",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}