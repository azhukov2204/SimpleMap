package ru.androidlearning.simplemap.storage.interactor

import com.google.android.gms.maps.model.LatLng
import ru.androidlearning.simplemap.domain.MarkerInfo
import ru.androidlearning.simplemap.storage.dto.MarkerDto
import ru.androidlearning.simplemap.storage.repository.MarkersRepository

class MarkersInteractorImpl(
    private val markersRepository: MarkersRepository
) : MarkersInteractor {

    override suspend fun saveMarker(markerInfo: MarkerInfo): Long =
        markersRepository.saveMarker(
            convertToMarkerDto(markerInfo)
        )

    override suspend fun deleteMarker(markerInfo: MarkerInfo) =
        markersRepository.deleteMarker(
            convertToMarkerDto(markerInfo)
        )

    override suspend fun getAllMarkers(): List<MarkerInfo> =
        markersRepository
            .getAllMarkers()
            .map { markerDto ->
                MarkerInfo(
                    id = markerDto.id,
                    position = LatLng(markerDto.latitude, markerDto.longitude),
                    title = markerDto.title
                )
            }

    private fun convertToMarkerDto(markerInfo: MarkerInfo): MarkerDto =
        MarkerDto(
            id = markerInfo.id,
            latitude = markerInfo.position.latitude,
            longitude = markerInfo.position.longitude,
            title = markerInfo.title
        )
}
