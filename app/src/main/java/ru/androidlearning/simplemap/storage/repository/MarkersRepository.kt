package ru.androidlearning.simplemap.storage.repository

import ru.androidlearning.simplemap.storage.dto.MarkerDto

interface MarkersRepository {
    suspend fun saveMarker(markerDto: MarkerDto): Long
    suspend fun deleteMarker(markerDto: MarkerDto)
    suspend fun getAllMarkers(): List<MarkerDto>
}