package ru.justd.abnamro.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import ru.justd.abnamro.R
import ru.justd.abnamro.app.App
import ru.justd.abnamro.app.di.AppComponent
import ru.justd.abnamro.app.model.Venue
import ru.justd.abnamro.list.view.VenueListViewState
import ru.justd.abnamro.list.view.VenueListWidget
import ru.justd.abnamro.utils.VerticalSpaceItemDecoration
import ru.justd.abnamro.utils.hideKeyboard
import ru.justd.duperadapter.ArrayListDuperAdapter
import ru.justd.lilwidgets.LilLoaderWidget


class VenueListActivity : AppCompatActivity() {

    //region ui

    @BindView(R.id.recycler)
    lateinit var recycler: RecyclerView

    @BindView(R.id.search_bar)
    lateinit var searchBar: EditText

    @BindView(R.id.loader)
    lateinit var loader: LilLoaderWidget

    private val adapter = ArrayListDuperAdapter()

    //endregion


    private lateinit var viewModel: VenueListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue_list)
        ButterKnife.bind(this)

        val graph = (application as App).graph

        //init architecture components view model
        initViewModel(graph)
        initUi()
    }

    private fun initViewModel(graph: AppComponent) {
        viewModel = ViewModelProviders.of(
                this,
                object : ViewModelProvider.Factory {

                    @Suppress("UNCHECKED_CAST")
                    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

                        val presenter = graph.getVenueListPresenter()
                        presenter.onStart()

                        return VenueListViewModel(presenter.view, presenter) as T
                    }

                }
        ).get(VenueListViewModel::class.java)

        viewModel.observe(this, Observer { state ->
            if (state != null) {
                onNewState(state)
            }
        })
    }

    private fun initUi() {
        adapter
                .addViewType<Venue, VenueListWidget>(Venue::class.java)
                .addViewCreator { viewGroup ->
                    val widget = VenueListWidget(viewGroup.context)
                    widget.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                    widget
                }
                .addViewBinder { widget, item -> widget.bind(item) }
                .addClickListener { _, item -> viewModel.presenter.showVenueDetailed(item.id)}
                .commit()

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelOffset(R.dimen.padding_8)))
        recycler.adapter = adapter

        searchBar = findViewById(R.id.search_bar)
        searchBar.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                window.decorView.clearFocus()
                hideKeyboard()

                viewModel.presenter.searchVenues(textView.text.toString())

            }
            true
        }
    }


    //region view updates

    private fun onNewState(state: VenueListViewState) {
        when (state) {
            is VenueListViewState.Loading -> showLoading()
            is VenueListViewState.Error -> showError()
            is VenueListViewState.Empty -> showEmpty()
            is VenueListViewState.Data -> showData(state.items)
        }
    }

    private fun showData(items: List<Venue>) {
        recycler.visibility = View.VISIBLE
        loader.hide()
        adapter.items.clear()
        adapter.addAll(items)
        adapter.notifyDataSetChanged()
    }

    private fun showEmpty() {
        recycler.visibility = View.INVISIBLE
        loader.showNoDataError()

    }

    private fun showError() {
        recycler.visibility = View.INVISIBLE
        loader.showNetworkError()

    }

    private fun showLoading() {
        recycler.visibility = View.INVISIBLE
        loader.showLoading()

    }

    //endregion

}
