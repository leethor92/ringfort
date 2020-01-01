package models

interface RingfortStore {
    fun findAll(): List<RingfortModel>
    fun findById(id:Long) : RingfortModel?
    fun create(ringfort: RingfortModel)
    fun update(ringfort: RingfortModel)
    fun delete(ringfort: RingfortModel)
    fun clear ()
}