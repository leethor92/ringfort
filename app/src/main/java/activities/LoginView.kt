package activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import main.MainApp
import models.UserModel
import org.jetbrains.anko.*
import org.wit.ringfort.R
import views.ringfortlist.RingfortListView

class LoginView : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        app = application as MainApp

        login.setOnClickListener() {
            val email: String = login_email.text.toString()
            val password: String = login_password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val allUsers: List<UserModel> = app.users.findAll()

                var foundUser: UserModel? = allUsers.find { user -> user.email == email && user.password == password }

                if (foundUser != null) {
                    startActivityForResult(intentFor<RingfortListView>().putExtra("current_user", foundUser), 0)
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
            startActivityForResult<SignupView>(0)
        }
    }
}