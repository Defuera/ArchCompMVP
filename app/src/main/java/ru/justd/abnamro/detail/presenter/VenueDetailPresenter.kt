package ru.justd.abnamro.detail.presenter

import ru.justd.abnamro.detail.view.VenueDetailView
import ru.justd.abnamro.list.model.VenueRepository
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class VenueDetailPresenter @Inject constructor(
        private val venueId : String,
        private val view: VenueDetailView,
        private val repository: VenueRepository
) {

    private val compositeDisposable = CompositeSubscription()

    fun onStart() {
        loadVenue(venueId)
    }

    private fun loadVenue(venueId: String) {
        view.showLoading()

        val subscription: Subscription = repository
                .getVenue(venueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { venue -> view.showItem(venue) },
                        { throwable -> view.showError(throwable) }
                )

        compositeDisposable.add(subscription)
    }

    fun onStop() {
        compositeDisposable.unsubscribe()
    }

}