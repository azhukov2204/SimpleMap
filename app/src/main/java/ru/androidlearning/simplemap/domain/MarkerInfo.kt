package ru.androidlearning.simplemap.domain

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarkerInfo(
    val id: Int? = null,
    val position: LatLng,
    val title: String
): Parcelable
