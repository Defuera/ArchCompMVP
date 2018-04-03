package ru.justd.abnamro.list.model

import ru.justd.abnamro.app.model.Venue
import rx.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoryVenueDataSource @Inject constructor() {

    private val dataMap = HashMap<String, Venue>()

    fun cache(venues: List<Venue>) {
        venues.forEach { dataMap[it.id] = it }
    }

    fun getById(venueId: String): Single<Venue> = Single.just(dataMap[venueId]) //todo can be null


}