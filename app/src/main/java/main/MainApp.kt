package main

import android.app.Application
import models.RingfortJSONStore
import models.RingfortMemStore
import models.RingfortStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.ringfort.R

class MainApp : Application(), AnkoLogger {

    lateinit var ringforts: RingfortStore

    override fun onCreate() {
        setTheme(R.style.AppTheme)
        super.onCreate()
        ringforts = RingfortJSONStore(applicationContext)
        info("Ringfort started")
    }
}