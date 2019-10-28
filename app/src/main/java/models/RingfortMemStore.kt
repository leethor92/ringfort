package models

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

    override fun create(ringfort: RingfortModel) {
        ringfort.id = getId()
        ringforts.add(ringfort)
        logAll()
    }

    override fun update(ringfort: RingfortModel) {
        var foundPlacemark: RingfortModel? = ringforts.find { p -> p.id == ringfort.id }
        if (foundPlacemark != null) {
            foundPlacemark.title = ringfort.title
            foundPlacemark.description = ringfort.description
            foundPlacemark.image = ringfort.image
            logAll()
        }
    }

    fun logAll() {
        ringforts.forEach { info("${it}") }
    }
}