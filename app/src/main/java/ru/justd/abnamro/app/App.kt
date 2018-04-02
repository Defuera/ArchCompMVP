package ru.justd.abnamro.app

import android.app.Application
import ru.justd.abnamro.app.di.AppComponent
import ru.justd.abnamro.app.di.AppModule
import ru.justd.abnamro.app.di.DaggerAppComponent

class App : Application() {

    lateinit var graph: AppComponent

    override fun onCreate() {
        super.onCreate()
        graph = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }
}