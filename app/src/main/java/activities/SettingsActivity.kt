package activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_ringfort.*
import kotlinx.android.synthetic.main.activity_settings.*
import main.MainApp
import models.UserModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.ringfort.R

class SettingsActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        app = application as MainApp

        //app.loginUser = intent.getParcelableExtra("user") as UserModel

        settings_email.setText(user.email)
        settings_password.setText(user.password)

        save_settings.setOnClickListener {
            var email: String = settings_email.text.toString()
            var password: String = settings_password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                user.email = email
                user.password = password
                app.users.update(user.copy())

                startActivityForResult(intentFor<RingfortListActivity>().putExtra("user", user), 1)
                setResult(AppCompatActivity.RESULT_OK)

            } else {
                toast("Enter email or password")
            }
        }

    }
}