package ru.justd.abnamro.detail.view

import android.arch.lifecycle.MutableLiveData
import ru.justd.abnamro.app.model.Venue
import javax.inject.Inject

class VenueDetailView @Inject constructor() {

    val liveData = MutableLiveData<VenueDetailViewState>()

    fun showItem(item: Venue) {
        liveData.postValue(VenueDetailViewState.Data(item))
    }

    fun showError(throwable: Throwable) {
        liveData.postValue(VenueDetailViewState.Error(throwable))
    }

    fun showLoading() {
        liveData.postValue(VenueDetailViewState.Loading())
    }


}

sealed class VenueDetailViewState {

    class Loading : VenueDetailViewState()
    class Empty : VenueDetailViewState()
    class Error(val throwable: Throwable) : VenueDetailViewState()
    data class Data(val item: Venue) : VenueDetailViewState()

}