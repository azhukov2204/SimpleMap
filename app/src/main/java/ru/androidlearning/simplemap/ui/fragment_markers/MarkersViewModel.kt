package ru.androidlearning.simplemap.ui.fragment_markers

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.androidlearning.simplemap.core.BaseMVVMViewModel
import ru.androidlearning.simplemap.core.UiState
import ru.androidlearning.simplemap.domain.MarkerInfo
import ru.androidlearning.simplemap.storage.interactor.MarkersInteractor

class MarkersViewModel(
    private val markersInteractor: MarkersInteractor
) : BaseMVVMViewModel<List<MarkerInfo>>() {

    fun getAllMarkers() {
        coroutineScopeIO.launch(
            CoroutineExceptionHandler { _, throwable -> doOnError(throwable) }
        ) {
            doOnSuccess(markersInteractor.getAllMarkers())
        }
    }

    fun updateMarker(marker: MarkerInfo) {
        coroutineScopeIO.launch(
            CoroutineExceptionHandler { _, throwable -> doOnError(throwable) }
        ) {
            markersInteractor.saveMarker(marker)
            doOnSuccess(markersInteractor.getAllMarkers())
        }
    }

    fun deleteMarker(marker: MarkerInfo) {
        coroutineScopeIO.launch(
            CoroutineExceptionHandler { _, throwable -> doOnError(throwable) }
        ) {
            markersInteractor.deleteMarker(marker)
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
