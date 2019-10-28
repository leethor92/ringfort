package models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RingfortModel(var id: Long = 0,
                         var image: String = "",
                         var title: String = "",
                          var description: String = "") : Parcelable