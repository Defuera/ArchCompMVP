package ru.justd.abnamro.list.model

import ru.justd.abnamro.app.model.Venue
import rx.Single
import javax.inject.Inject

class VenueRepository @Inject constructor(
        private val remote: ApiVenueDataSource
//        private val remote : ApiVenueDataSource
) {

    fun searchVenue(name: String): Single<List<Venue>> {
        return remote.search(name)
    }

}