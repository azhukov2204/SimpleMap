package ru.androidlearning.simplemap.storage.datasource

import ru.androidlearning.simplemap.storage.dao.MarkersDao
import ru.androidlearning.simplemap.storage.db.MarkersDatabase
import ru.androidlearning.simplemap.storage.dto.MarkerDto

class MarkersDatasourceImpl(
    private val markersDatabase: MarkersDatabase
) : MarkersDatasource {

    private val markersDao: MarkersDao
        get() = markersDatabase.getMarkersDao()

    override suspend fun saveMarker(markerDto: MarkerDto): Long =
        markersDao.saveMarker(markerDto)


    override suspend fun deleteMarker(markerDto: MarkerDto) =
        markersDao.deleteMarker(markerDto)

    override suspend fun getAllMarkers(): List<MarkerDto> =
        markersDao.getAllMarkers()
}
