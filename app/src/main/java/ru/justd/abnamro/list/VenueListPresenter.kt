package ru.justd.abnamro.list

import ru.justd.abnamro.list.model.VenueInteractor
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class VenueListPresenter constructor(
        private val view: VenueListView,
        private val interactor: VenueInteractor
) {

    val compositeDisposable = CompositeSubscription()

    fun onStart() {

    }

    fun searchVenues(name: String) {
        view.showLoading()
        interactor
                .searchVenue(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { items -> view.showItems(items) },
                        { throwable -> view.showError(throwable) }
                )
    }


    fun CompositeSubscription.plus(disposable: rx.Subscription) {
        this.add(disposable)
    }
}