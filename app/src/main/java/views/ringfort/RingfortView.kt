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
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import helpers.readImageFromPath
import helpers.showImagePicker
import kotlinx.android.synthetic.main.activity_ringfort.*
import kotlinx.android.synthetic.main.activity_ringfort.description
import kotlinx.android.synthetic.main.activity_ringfort.ringfortTitle
import kotlinx.android.synthetic.main.card_ringfort.*
import kotlinx.android.synthetic.main.content_ringfort_maps.*
import main.MainApp
import models.Location
import org.jetbrains.anko.info
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.ringfort.R
import models.RingfortModel
import org.jetbrains.anko.intentFor
import org.wit.placemark.activities.RingfortPresenter
import views.BaseView
import java.text.SimpleDateFormat
import java.util.*

class RingfortView : BaseView(), AnkoLogger {

    var ringfort = RingfortModel()
    lateinit var presenter: RingfortPresenter
    lateinit var map: GoogleMap

    var shareActionProvider: ShareActionProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ringfort)

        init(toolbarAdd, true)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = RingfortPresenter(this)

        //presenter.doAddOrSave(ringfortTitle.text.toString(), description.text.toString(), checkBox.isChecked, addNotes.text.toString(), dateVisited.text.toString())

        chooseImage.setOnClickListener { presenter.doSelectImage() }

        //ringfortLocation.setOnClickListener {
        //   presenter.doSetLocation()
        //}

        mapView2.onCreate(savedInstanceState);

        mapView2.getMapAsync {
            presenter.doConfigureMap(it)
            it.setOnMapClickListener { presenter.doSetLocation() }
        }

        ringfortNavigateButton.setOnClickListener {
            presenter.doNavigation()
        }
    }

    override fun showRingfort(ringfort: RingfortModel) {
        ringfortTitle.setText(ringfort.title)
        description.setText(ringfort.description)
        addNotes.setText(ringfort.notes)
        checkBox.isChecked = ringfort.visited
        dateVisited.setText(ringfort.date)
        rating.rating = ringfort.rating
        favouriteCheckbox.isChecked = ringfort.favourite
        Glide.with(this).load(ringfort.image).into(ringfortImage)

        if (ringfort.image != null) {
            chooseImage.setText(R.string.change_ringfort_image)
        }

        lat.setText("%.6f".format(ringfort.lat))
        lng.setText("%.6f".format(ringfort.lng))
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ringfort, menu)

        shareActionProvider = MenuItemCompat.getActionProvider(menu?.findItem(R.id.item_share)) as ShareActionProvider
        shareActionProvider?.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME)
        shareActionProvider?.setShareIntent(presenter.createShareIntent())

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_save -> {
                if (ringfortTitle.text.toString().isEmpty()) {
                    toast("Enter a title")
                } else {
                    presenter.doAddOrSave(ringfortTitle.text.toString(), description.text.toString(), checkBox.isChecked, addNotes.text.toString(), dateVisited.text.toString(), favouriteCheckbox.isChecked, rating.rating)
                }
            }
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

    override fun onBackPressed() {
        presenter.doCancel()
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

    override fun onDestroy() {
        super.onDestroy()
        mapView2.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView2.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView2.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView2.onResume()
        //presenter.doResartLocationUpdates()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView2.onSaveInstanceState(outState)
    }
}
