package ru.justd.abnamro.detail

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import ru.justd.abnamro.detail.presenter.VenueDetailPresenter
import ru.justd.abnamro.detail.view.VenueDetailView
import ru.justd.abnamro.detail.view.VenueDetailViewState
import ru.justd.abnamro.list.presenter.VenueListPresenter
import ru.justd.abnamro.list.view.VenueListView
import ru.justd.abnamro.list.view.VenueListViewState

class VenueDetailViewModel(
        val view: VenueDetailView,
        val presenter: VenueDetailPresenter
) : ViewModel() {
    fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<VenueDetailViewState>) {
        view.liveData.observe(lifecycleOwner, observer)
    }

    override fun onCleared() {
        presenter.onStop()
    }
}