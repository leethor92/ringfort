package models.mem

import models.RingfortModel
import models.RingfortStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class RingfortMemStore : RingfortStore, AnkoLogger {

    val ringforts = ArrayList<RingfortModel>()

    override fun findAll(): List<RingfortModel> {
        return ringforts
    }

    override fun findById(id:Long) : RingfortModel? {
        val foundRingfort: RingfortModel? = ringforts.find { it.id == id }
        return foundRingfort
    }

    override fun create(ringfort: RingfortModel) {
        ringfort.id = getId()
        ringforts.add(ringfort)
        logAll()
    }

    override fun update(ringfort: RingfortModel) {
        var foundRingfort: RingfortModel? = ringforts.find { p -> p.id == ringfort.id }
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
            logAll()
        }
    }

    fun logAll() {
        ringforts.forEach { info("${it}") }
    }

    override fun delete(ringfort: RingfortModel) {
        ringforts.remove(ringfort)
    }

    override fun clear() {
        ringforts.clear()
    }
}