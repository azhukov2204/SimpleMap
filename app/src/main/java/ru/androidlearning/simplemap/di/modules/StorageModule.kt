package ru.androidlearning.simplemap.di.modules

import androidx.room.Room
import org.koin.dsl.module
import ru.androidlearning.simplemap.storage.datasource.MarkersDatasource
import ru.androidlearning.simplemap.storage.datasource.MarkersDatasourceImpl
import ru.androidlearning.simplemap.storage.db.MarkersDatabase
import ru.androidlearning.simplemap.storage.interactor.MarkersInteractor
import ru.androidlearning.simplemap.storage.interactor.MarkersInteractorImpl
import ru.androidlearning.simplemap.storage.repository.MarkersRepository
import ru.androidlearning.simplemap.storage.repository.MarkersRepositoryImpl

private const val DB_FILE_NAME = "markers.db"

val storageModule = module {
    single {
        Room.databaseBuilder(get(), MarkersDatabase::class.java, DB_FILE_NAME)
            .build()
    }
    factory<MarkersDatasource> { MarkersDatasourceImpl(markersDatabase = get()) }
    factory<MarkersRepository> { MarkersRepositoryImpl(markersDatasource = get()) }
    factory<MarkersInteractor> { MarkersInteractorImpl(markersRepository = get()) }
}
