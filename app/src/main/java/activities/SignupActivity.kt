package activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signup.*
import main.MainApp
import models.UserModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.ringfort.R

class SignupActivity  : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        app = application as MainApp

        register.setOnClickListener() {
            user.email = email.text.toString()
            user.password = password.text.toString()
            if (user.email.isNotEmpty() && user.password.isNotEmpty()) {
                if (app.users.signup(user)) {
                    app.loginUser = user
                    startActivityForResult<RingfortListActivity>(0)
                }
                else {
                    toast("A user with this email already exists")
                }
            }
            else {
                toast("Email and password are required")
            }
        }

        register_login.setOnClickListener() {
            startActivityForResult<LoginActivity>(0)
        }
    }
}