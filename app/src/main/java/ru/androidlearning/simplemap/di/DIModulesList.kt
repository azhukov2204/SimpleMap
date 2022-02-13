package ru.androidlearning.simplemap.di

import ru.androidlearning.simplemap.di.modules.ciceroneModule
import ru.androidlearning.simplemap.di.modules.mapViewModelModule
import ru.androidlearning.simplemap.di.modules.markersViewModelModule
import ru.androidlearning.simplemap.di.modules.storageModule

val diModules = listOf(
    ciceroneModule,
    mapViewModelModule,
    markersViewModelModule,
    storageModule
)
