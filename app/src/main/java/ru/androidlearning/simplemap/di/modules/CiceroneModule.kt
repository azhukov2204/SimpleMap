package ru.androidlearning.simplemap.di.modules

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.androidlearning.simplemap.MainActivity

val ciceroneModule = module {
    val cicerone: Cicerone<Router> = Cicerone.create()
    single { cicerone.router }
    scope(named<MainActivity>()) {
        scoped { cicerone.getNavigatorHolder() }
    }
}