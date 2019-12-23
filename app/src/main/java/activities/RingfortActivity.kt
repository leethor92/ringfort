package activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import helpers.readImageFromPath
import helpers.showImagePicker
import kotlinx.android.synthetic.main.activity_ringfort.*
import kotlinx.android.synthetic.main.activity_ringfort.description
import kotlinx.android.synthetic.main.activity_ringfort.ringfortTitle
import kotlinx.android.synthetic.main.card_ringfort.*
import main.MainApp
import models.Location
import org.jetbrains.anko.info
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.ringfort.R
import models.RingfortModel
import org.jetbrains.anko.intentFor
import org.wit.placemark.activities.RingfortPresenter
import java.text.SimpleDateFormat
import java.util.*

class RingfortActivity : AppCompatActivity(), AnkoLogger {

    var ringfort = RingfortModel()
    lateinit var presenter: RingfortPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ringfort)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = RingfortPresenter(this)

        btnAdd.setOnClickListener {
            if (ringfortTitle.text.toString().isEmpty()) {
                toast(R.string.enter_ringfort_title)
            } else {
                presenter.doAddOrSave(ringfortTitle.text.toString(), description.text.toString(), checkBox.isChecked, addNotes.text.toString(), dateVisited.text.toString())
            }
        }

        chooseImage.setOnClickListener { presenter.doSelectImage() }

        ringfortLocation.setOnClickListener {
           presenter.doSetLocation()
        }

    }

    fun showRingfort(ringfort: RingfortModel) {
        ringfortTitle.setText(ringfort.title)
        description.setText(ringfort.description)
        addNotes.setText(ringfort.notes)
        checkBox.isChecked = ringfort.visited
        dateVisited.setText(ringfort.date)

        if (ringfort.images.size > 0) {
            for (image in ringfort.images) {
                val imageView: ImageView = ImageView(this)
                var params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                    600,
                    600
                )
                params.setMargins(0, 0, 10, 0)
                imageView.layoutParams = params
                imageView.setImageBitmap(readImageFromPath(this, image))
                imageGallery.addView(imageView)
            }
            if (ringfort.images[0] != null) {
                chooseImage.setText(R.string.change_ringfort_image)
            }
        }
        btnAdd.setText(R.string.save_ringfort)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ringfort, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
                finish()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            presenter.doActivityResult(requestCode, resultCode, data)
        }
    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            when (view.id) {
                R.id.checkBox -> {
                    info ("Visited $ringfort")
                    val formatD = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                    val currentDate = formatD.format(Date())

                    if (checkBox.isChecked) {
                        ringfort.visited = true
                        ringfort.date = currentDate
                    }

                    if (checkBox.isChecked.not())
                    {
                        ringfort.visited = false
                        ringfort.date = "Not visited yet"
                    }
                }
            }
        }
    }
}
