package ru.justd.abnamro.detail

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.support.v7.app.AppCompatActivity

class VenueDetailActivity : AppCompatActivity() {

    companion object {

        private const val EXTRA_VENUE_ID = "EXTRA_VENUE_ID"

        @JvmStatic
        fun newIntent(context: Context, venueId: String): Intent {
            val intent = Intent(context, VenueDetailActivity::class.java)
            intent.putExtra(EXTRA_VENUE_ID, venueId)
            intent.flags = FLAG_ACTIVITY_NEW_TASK
            return intent
        }
    }

}