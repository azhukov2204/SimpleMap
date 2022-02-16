package ru.androidlearning.simplemap.storage.interactor

import ru.androidlearning.simplemap.domain.MarkerInfo

interface MarkersInteractor {
    suspend fun saveMarker(markerInfo: MarkerInfo): Long
    suspend fun deleteMarker(markerInfo: MarkerInfo)
    suspend fun getAllMarkers(): List<MarkerInfo>
}
