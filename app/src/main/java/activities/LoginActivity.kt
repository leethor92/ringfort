package activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import main.MainApp
import models.UserModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.ringfort.R

class LoginActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        app = application as MainApp

        login.setOnClickListener() {
            var email: String = login_email.text.toString()
            var password: String = login_password.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                var user = app.users.login(email, password)
                if (user != null) {
                    app.loginUser = user
                    startActivityForResult<RingfortListActivity>(0)
                }
                else {
                    toast("Email or password entered was entered incorrectly")
                }
            }
            else {
                toast("Email and password are required")
            }
        }

        login_register.setOnClickListener() {
            startActivityForResult<SignupActivity>(0)
        }
    }
}