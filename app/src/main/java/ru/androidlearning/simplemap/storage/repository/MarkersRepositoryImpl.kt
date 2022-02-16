package ru.androidlearning.simplemap.storage.repository

import ru.androidlearning.simplemap.storage.datasource.MarkersDatasource
import ru.androidlearning.simplemap.storage.dto.MarkerDto

class MarkersRepositoryImpl(
    private val markersDatasource: MarkersDatasource
) : MarkersRepository {

    override suspend fun saveMarker(markerDto: MarkerDto): Long =
        markersDatasource.saveMarker(markerDto)

    override suspend fun deleteMarker(markerDto: MarkerDto) =
        markersDatasource.deleteMarker(markerDto)

    override suspend fun getAllMarkers(): List<MarkerDto> =
        markersDatasource.getAllMarkers()
}
