package models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RingfortModel(var title: String = "",
                          var description: String = "") : Parcelable