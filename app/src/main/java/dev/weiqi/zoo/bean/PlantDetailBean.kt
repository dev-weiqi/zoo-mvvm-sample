package dev.weiqi.zoo.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlantDetailBean(
    val title: String,
    val desc: String
) : Parcelable