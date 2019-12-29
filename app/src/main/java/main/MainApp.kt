package main

import android.app.Application
import models.*
import models.json.RingfortJSONStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.ringfort.R

class MainApp : Application(), AnkoLogger {

    lateinit var ringforts: RingfortStore
    lateinit var users: UserStore
    lateinit var loginUser: UserModel

    override fun onCreate() {
        setTheme(R.style.AppTheme)
        super.onCreate()
        ringforts = RingfortJSONStore(applicationContext)
        users = UserJSONStore(applicationContext)
        info("Ringfort started")
    }
}