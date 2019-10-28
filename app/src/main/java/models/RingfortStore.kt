package models

interface RingfortStore {
    fun findAll(): List<RingfortModel>
    fun create(ringfort: RingfortModel)
}