package com.example.weatherr.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val city: String,
    var lat: Double,
    val lon: Double
) : Parcelable
