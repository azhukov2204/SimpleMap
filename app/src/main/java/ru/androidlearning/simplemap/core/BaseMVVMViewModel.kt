package ru.androidlearning.simplemap.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

abstract class BaseMVVMViewModel<T> : ViewModel() {

    protected val dataLoadingLiveData = MutableLiveData<UiState<T>>()
    fun getLiveData(): LiveData<UiState<T>> = dataLoadingLiveData

    protected val coroutineScopeIO by lazy {
        CoroutineScope(
            Dispatchers.IO
                    + SupervisorJob()
                    + CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }
        )
    }

    override fun onCleared() {
        coroutineScopeIO.cancel()
        super.onCleared()
    }
}
