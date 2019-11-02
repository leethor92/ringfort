package models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RingfortModel(var id: Long = 0,
                         var images:ArrayList<String> = ArrayList(),
                         var title: String = "",
                          var description: String = "",
                         var lat : Double = 0.0,
                         var lng: Double = 0.0,
                         var zoom: Float = 0f,
                         var visited: Boolean = false) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable