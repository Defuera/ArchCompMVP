package ru.justd.abnamro.list

import android.arch.lifecycle.MutableLiveData
import ru.justd.abnamro.app.Venue

class VenueListView {

    val liveData = MutableLiveData<VenueListViewState>()

    fun showItems(items: List<Venue>) {
        liveData.postValue(VenueListViewState.Data(items))
    }

    fun showError(throwable: Throwable) {
        liveData.postValue(VenueListViewState.Error(throwable))
    }

    fun showLoading() {
        liveData.postValue(VenueListViewState.Loading())
    }


}

sealed class VenueListViewState {

    class Loading : VenueListViewState()
    class Empty : VenueListViewState()
    class Error(val throwable: Throwable) : VenueListViewState()
    data class Data(val items: List<Venue>) : VenueListViewState()

}