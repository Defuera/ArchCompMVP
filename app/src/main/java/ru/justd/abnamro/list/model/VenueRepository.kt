package ru.justd.abnamro.list.model

import ru.justd.abnamro.app.model.Venue
import rx.Single
import javax.inject.Inject

class VenueRepository @Inject constructor(
        private val remote: ApiVenueDataSource,
        private val local : MemoryVenueDataSource
) {

    fun searchVenue(name: String): Single<List<Venue>> {
        return remote
                .search(name)
                .doOnSuccess { local.cache(it) }
    }

    fun getVenue(venueId: String): Single<Venue> {
        return local.getById(venueId)
                //todo implement loading from remote if venue is not cached (can happen on activity loaded out of memory)
    }

}