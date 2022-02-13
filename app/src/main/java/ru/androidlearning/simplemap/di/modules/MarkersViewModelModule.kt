package ru.androidlearning.simplemap.di.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.androidlearning.simplemap.ui.fragment_markers.MarkersFragment
import ru.androidlearning.simplemap.ui.fragment_markers.MarkersViewModel

val markersViewModelModule = module {
    scope(named<MarkersFragment>()) {
        viewModel { MarkersViewModel(markersInteractor = get()) }
    }
}
