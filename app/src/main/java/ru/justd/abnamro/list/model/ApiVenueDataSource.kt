package ru.justd.abnamro.list.model

import ru.justd.abnamro.app.model.Venue
import ru.justd.abnamro.app.model.api.ApiError
import ru.justd.abnamro.app.model.api.VenueApi
import rx.Single
import javax.inject.Inject

private const val HARDCODED_PARAM_NEAR = "Amsterdam"

class ApiVenueDataSource @Inject constructor(
        private val api: VenueApi
) {

    fun search(name: String): Single<List<Venue>> {
        return api
                .search(HARDCODED_PARAM_NEAR, name)
                .map { wrapper ->
                    val code = wrapper.meta.code
                    if (code < 200 || code > 299) {
                        throw ApiError(code)
                    } else {
                        wrapper.response.venues
                    }
                }
    }


}