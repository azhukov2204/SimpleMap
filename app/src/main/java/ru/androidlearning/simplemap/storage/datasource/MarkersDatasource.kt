package ru.androidlearning.simplemap.storage.datasource

import ru.androidlearning.simplemap.storage.dto.MarkerDto

interface MarkersDatasource {
    suspend fun saveMarker(markerDto: MarkerDto): Long
    suspend fun deleteMarker(markerDto: MarkerDto)
    suspend fun getAllMarkers(): List<MarkerDto>
}
