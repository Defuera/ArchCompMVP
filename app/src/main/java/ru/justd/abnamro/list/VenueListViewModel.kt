package ru.justd.abnamro.list

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel

class VenueListViewModel(
        val view: VenueListView,
        val presenter: VenueListPresenter
) : ViewModel() {
    fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<VenueListViewState>) {
        view.liveData.observe(lifecycleOwner, observer)
    }
}