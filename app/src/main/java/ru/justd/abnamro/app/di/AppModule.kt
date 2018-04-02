package ru.justd.abnamro.app.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.justd.abnamro.app.Navigation
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    fun providesNavigation() = Navigation(context)

}