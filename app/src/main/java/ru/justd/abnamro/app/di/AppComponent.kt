package ru.justd.abnamro.app.di

import dagger.Component
import ru.justd.abnamro.list.presenter.VenueListPresenter
import ru.justd.abnamro.list.view.VenueListView
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun getVenueListView(): VenueListView
    fun getVenueListPresenter(): VenueListPresenter
}
