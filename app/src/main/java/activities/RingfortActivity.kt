package activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_ringfort.*
import main.MainApp
import org.jetbrains.anko.info
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.ringfort.R
import models.RingfortModel

class RingfortActivity : AppCompatActivity(), AnkoLogger {

    var ringfort = RingfortModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ringfort)
        app = application as MainApp

        btnAdd.setOnClickListener() {
            ringfort.title = ringfortTitle.text.toString()
            ringfort.description = description.text.toString()
            if(ringfort.title.isNotEmpty()) {
                app.ringforts.add(ringfort.copy())
                info("add Button Pressed: ${ringfort}")
                for (i in app.ringforts.indices) {
                    info("Ringfort[$i]:${app.ringforts[i]}")
                }
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
            else
            {
                toast("Please Enter a title")
            }
        }
    }
}
