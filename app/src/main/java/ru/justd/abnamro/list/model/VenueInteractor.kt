package ru.justd.abnamro.list.model

import ru.justd.abnamro.app.model.Venue
import rx.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class VenueInteractor @Inject constructor(
        private val repository: VenueRepository
) {

    fun searchVenue(name: String): Single<List<Venue>> {
        return repository
                .searchVenue(name)
                .delay(1, TimeUnit.SECONDS)
    }

}