package ru.androidlearning.simplemap.di.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.androidlearning.simplemap.ui.fragment_map.MapFragment
import ru.androidlearning.simplemap.ui.fragment_map.MapViewModel

val mapViewModelModule = module {
    scope(named<MapFragment>()) {
        viewModel { MapViewModel(markersInteractor = get()) }
    }
}