package ru.androidlearning.simplemap.ui.fragment_map

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.androidlearning.simplemap.core.BaseMVVMViewModel
import ru.androidlearning.simplemap.core.UiState
import ru.androidlearning.simplemap.domain.MarkerInfo
import ru.androidlearning.simplemap.storage.interactor.MarkersInteractor

class MapViewModel(
    private val markersInteractor: MarkersInteractor
) : BaseMVVMViewModel<List<MarkerInfo>>() {

    fun saveMarker(markerInfo: MarkerInfo) {
        coroutineScopeIO.launch(
            CoroutineExceptionHandler { _, throwable -> doOnError(throwable) }
        ) {
            markersInteractor.saveMarker(markerInfo)
            doOnSuccess(markersInteractor.getAllMarkers())
        }
    }

    fun getAllMarkers() {
        coroutineScopeIO.launch(
            CoroutineExceptionHandler { _, throwable -> doOnError(throwable) }
        ) {
            doOnSuccess(markersInteractor.getAllMarkers())
        }
    }

    private fun doOnSuccess(markers: List<MarkerInfo>) {
        dataLoadingLiveData.postValue(UiState.Success(markers))
    }

    private fun doOnError(throwable: Throwable) {
        dataLoadingLiveData.postValue(UiState.Error(throwable))
    }
}
