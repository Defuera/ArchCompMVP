package ru.justd.abnamro.list

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import ru.justd.abnamro.list.presenter.VenueListPresenter
import ru.justd.abnamro.list.view.VenueListView
import ru.justd.abnamro.list.view.VenueListViewState

class VenueListViewModel(
        val view: VenueListView,
        val presenter: VenueListPresenter
) : ViewModel() {
    fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<VenueListViewState>) {
        view.liveData.observe(lifecycleOwner, observer)
    }

    override fun onCleared() {
        presenter.onStop()
    }
}