package ru.justd.abnamro.app

import android.content.Context
import ru.justd.abnamro.detail.VenueDetailActivity

class Navigation(private val context: Context) {

    fun showVenueDetailed(venueId: String) {
        context.startActivity(VenueDetailActivity.newIntent(context, venueId))
    }
}