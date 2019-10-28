package activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import helpers.readImage
import helpers.readImageFromPath
import helpers.showImagePicker
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
    val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ringfort)
        app = application as MainApp
        var edit = false
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        info("Placemark Activity started..")

        if (intent.hasExtra("ringfort_edit")) {
            edit = true
            ringfort = intent.extras?.getParcelable<RingfortModel>("ringfort_edit")!!
            ringfortTitle.setText(ringfort.title)
            description.setText(ringfort.description)
            btnAdd.setText(R.string.save_ringfort)
            ringfortImage.setImageBitmap(readImageFromPath(this, ringfort.image))
            if (ringfort.image != null) {
                chooseImage.setText(R.string.change_ringfort_image)
            }
        }

        btnAdd.setOnClickListener() {
            ringfort.title = ringfortTitle.text.toString()
            ringfort.description = description.text.toString()
            if (ringfort.title.isEmpty()) {
                toast(R.string.enter_ringfort_title)
            } else {
                if (edit) {
                    app.ringforts.update(ringfort.copy())
                } else {
                    app.ringforts.create(ringfort.copy())
                }
            }
            info("add Button Pressed: $ringfortTitle")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ringfort, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    ringfort.image = data.getData().toString()
                    ringfortImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_ringfort_image)
                }
            }
        }
    }
}
