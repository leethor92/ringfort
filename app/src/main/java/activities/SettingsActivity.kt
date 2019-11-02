package activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_ringfort.*
import kotlinx.android.synthetic.main.activity_settings.*
import main.MainApp
import models.UserModel
import org.jetbrains.anko.*
import org.wit.ringfort.R

class SettingsActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp

    var current_user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        app = application as MainApp

        if (intent.hasExtra("current_user")) {
            current_user = intent.extras?.getParcelable<UserModel>("current_user")!!
            settings_email.setText(current_user.email)
            settings_password.setText(current_user.password)
            info("This is the logged in user: $current_user")
        }

        save_settings.setOnClickListener {
            current_user.email = settings_email.text.toString()
            current_user.password = settings_password.text.toString()

            if (current_user.email.isNotEmpty() && current_user.password.isNotEmpty()) {

                app.users.update(current_user.copy())

                startActivityForResult(intentFor<RingfortListActivity>().putExtra("user", current_user), 0)
                finish()

            } else {
                toast("Enter email or password")
            }
        }

    }
}