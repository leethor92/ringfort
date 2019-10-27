package activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_ringfort.*
import org.jetbrains.anko.info
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.ringfort.R
import models.RingfortModel

class RingfortActivity : AppCompatActivity(), AnkoLogger {

    var ringfort = RingfortModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ringfort)

        btnAdd.setOnClickListener() {
            ringfort.title = ringfortTitle.text.toString()
            if(ringfort.title.isNotEmpty()) {
                info("add Button Pressed: $ringfort")
            }
            else
            {
                toast("Please Enter a title")
            }
        }
    }
}
