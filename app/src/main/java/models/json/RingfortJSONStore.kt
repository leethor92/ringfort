package models.json

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import helpers.*
import models.RingfortModel
import models.RingfortStore
import java.util.*

val JSON_FILE = "ringforts.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<RingfortModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class RingfortJSONStore : RingfortStore, AnkoLogger {

    val context: Context
    var ringforts = mutableListOf<RingfortModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<RingfortModel> {
        return ringforts
    }

    override fun findById(id:Long) : RingfortModel? {
        val foundRingfort: RingfortModel? = ringforts.find { it.id == id }
        return foundRingfort
    }

    override fun create(ringfort: RingfortModel) {
        ringfort.id = generateRandomId()
        ringforts.add(ringfort)
        serialize()
    }


    override fun update(ringfort: RingfortModel) {
        val ringfortsList = findAll() as ArrayList<RingfortModel>
        var foundRingfort: RingfortModel? = ringfortsList.find { p -> p.id == ringfort.id }
        if (foundRingfort != null) {
            foundRingfort.title = ringfort.title
            foundRingfort.description = ringfort.description
            foundRingfort.image = ringfort.image
            foundRingfort.lat = ringfort.lat
            foundRingfort.lng = ringfort.lng
            foundRingfort.zoom = ringfort.zoom
            foundRingfort.visited = ringfort.visited
            foundRingfort.notes = ringfort.notes
            foundRingfort.date = ringfort.date
            foundRingfort.rating = ringfort.rating
            foundRingfort.favourite = ringfort.favourite
        }
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(ringforts, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        ringforts = Gson().fromJson(jsonString, listType)
    }

    override fun delete(ringfort: RingfortModel) {
        ringforts.remove(ringfort)
        serialize()
    }

    override fun clear() {
        ringforts.clear()
    }
}