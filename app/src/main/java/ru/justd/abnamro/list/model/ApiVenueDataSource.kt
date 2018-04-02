package ru.justd.abnamro.list.model

import ru.justd.abnamro.app.model.Venue
import ru.justd.abnamro.app.model.VenueApi
import rx.Single
import javax.inject.Inject

private const val HARDCODED_PARAM_NEAR = "Amsterdam"

class ApiVenueDataSource @Inject constructor(
        private val api: VenueApi
) {

    fun search(name : String): Single<List<Venue>> {
        return api.search(HARDCODED_PARAM_NEAR, name)
    }

}