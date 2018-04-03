package ru.justd.abnamro.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import ru.justd.abnamro.R
import ru.justd.abnamro.app.App
import ru.justd.abnamro.app.di.AppComponent
import ru.justd.abnamro.app.model.Venue
import ru.justd.abnamro.detail.presenter.VenueDetailPresenter
import ru.justd.abnamro.detail.view.VenueDetailView
import ru.justd.abnamro.detail.view.VenueDetailViewState
import ru.justd.lilwidgets.LilLoaderWidget

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

    @BindView(R.id.image)
    lateinit var image: ImageView

    @BindView(R.id.name)
    lateinit var name: TextView

    @BindView(R.id.description)
    lateinit var description: TextView

    @BindView(R.id.contacts)
    lateinit var contacts: TextView

    @BindView(R.id.data_container)
    lateinit var container: View

    @BindView(R.id.loader)
    lateinit var loader: LilLoaderWidget


    lateinit var viewModel: VenueDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue_detailed)
        ButterKnife.bind(this)

        val graph = (application as App).graph

        initViewModel(graph)
    }

    private fun initViewModel(graph: AppComponent) {
        viewModel = ViewModelProviders.of(
                this,
                object : ViewModelProvider.Factory {

                    @Suppress("UNCHECKED_CAST")
                    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

                        val view = VenueDetailView()
                        val presenter = VenueDetailPresenter(
                                intent.getStringExtra(EXTRA_VENUE_ID),
                                view,
                                graph.getVenueRepository()
                        )
                        presenter.onStart()

                        return VenueDetailViewModel(view, presenter) as T
                    }

                }
        ).get(VenueDetailViewModel::class.java)

        viewModel.observe(this, Observer { state ->
            if (state != null) {
                onNewState(state)
            }
        })
    }


    //region view updates

    private fun onNewState(state: VenueDetailViewState) {
        when (state) {
            is VenueDetailViewState.Loading -> showLoading()
            is VenueDetailViewState.Error -> showError()
            is VenueDetailViewState.Empty -> showEmpty()
            is VenueDetailViewState.Data -> showData(state.item)
        }
    }

    private fun showData(venue: Venue) {
//        image.  //todo Picasso
        name.text = venue.name
//        description.text = venue.de //todo what is description?
        contacts.text = "Phone: ${venue.contact.formattedPhone}"

        loader.hide()
        container.visibility = View.VISIBLE
    }

    private fun showEmpty() {
        container.visibility = View.INVISIBLE
        loader.showNoDataError()

    }

    private fun showError() {
        container.visibility = View.INVISIBLE
        loader.showNetworkError()

    }

    private fun showLoading() {
        container.visibility = View.INVISIBLE
        loader.showLoading()

    }

    //endregion


}