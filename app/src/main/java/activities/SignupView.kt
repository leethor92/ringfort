package activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signup.*
import main.MainApp
import models.UserModel
import org.jetbrains.anko.*
import org.wit.ringfort.R
import views.ringfortlist.RingfortListView

class SignupView  : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var newUser = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        app = application as MainApp

        register.setOnClickListener() {
            val email = email.text.toString()
            val password = password.text.toString()

            val allUsers: List<UserModel> = app.users.findAll()

            var foundUser: UserModel? = allUsers.find { user -> user.email == email }

            if (foundUser == null) {
                newUser.email = email
                newUser.password = password

                if (newUser.email.isNotEmpty() && newUser.password.isNotEmpty()) {
                    app.users.create(newUser.copy())

                    startActivity<RingfortListView>()
                    info("Signup successful $")
                } else {
                    toast("Please enter both email and password")
                }


            } else {
                toast("Please use a different email this one is taken")
            }
        }

        register_login.setOnClickListener() {
            startActivityForResult<LoginView>(0)
        }
    }
}