package ru.justd.abnamro.list.model

import android.util.Log
import ru.justd.abnamro.app.Contact
import ru.justd.abnamro.app.Location
import ru.justd.abnamro.app.Venue
import rx.Single
import java.util.concurrent.TimeUnit

class VenueInteractor {

    fun searchVenue(name: String): Single<List<Venue>> {
        Log.i("DensTest", "test: $name")
        return Single.just(
                listOf(
                        Venue(
                                "one",
                                Location(
                                        "address",
                                        "Moscow", "No", "Russia", 0.0, 0.1
                                ),
                                "Noname",
                                Contact()
                        )
                )
        )
                .delay(1, TimeUnit.SECONDS)

    }
}