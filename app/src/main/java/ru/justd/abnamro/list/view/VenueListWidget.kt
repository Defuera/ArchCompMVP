package ru.justd.abnamro.list.view

import android.content.Context
import android.support.v7.widget.CardView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import ru.justd.abnamro.R
import ru.justd.abnamro.app.model.Venue

class VenueListWidget(context: Context) : CardView(context) {

    @BindView(R.id.name)
    lateinit var nameTextView: TextView

    @BindView(R.id.address)
    lateinit var addressTextView: TextView

    init {
        View.inflate(context, R.layout.widget_venue_list_item, this)
        ButterKnife.bind(this)
    }

    fun bind(venue: Venue) {
        nameTextView.text = venue.name
        addressTextView.text = venue.location.address
    }

}