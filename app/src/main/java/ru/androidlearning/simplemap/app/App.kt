package ru.androidlearning.simplemap.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.androidlearning.simplemap.di.diModules

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(diModules)
        }
    }
}