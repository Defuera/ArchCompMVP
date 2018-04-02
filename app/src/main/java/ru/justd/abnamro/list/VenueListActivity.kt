package ru.justd.abnamro.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import ru.justd.abnamro.R
import ru.justd.abnamro.app.Venue
import ru.justd.abnamro.list.model.VenueInteractor
import ru.justd.abnamro.list.view.VenueListWidget
import ru.justd.duperadapter.ArrayListDuperAdapter
import ru.justd.lilwidgets.LilLoaderWidget


class VenueListActivity : AppCompatActivity() {

    //uiu
    lateinit var recycler: RecyclerView
    lateinit var searchBar: EditText
    lateinit var loader: LilLoaderWidget

    private val adapter = ArrayListDuperAdapter()

    //lifecycle
    lateinit var viewModel: VenueListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue_list)

        //init architecture components view model
        viewModel = ViewModelProviders.of(
                this,
                object : ViewModelProvider.Factory {

                    @Suppress("UNCHECKED_CAST")
                    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

                        val view = VenueListView()
                        val presenter = VenueListPresenter(view, VenueInteractor())
                        presenter.onStart()

                        return VenueListViewModel(view, presenter) as T
                    }

                }
        ).get(VenueListViewModel::class.java)


        initUi()


        viewModel.observe(this, Observer { state ->
            if (state != null) {
                onNewState(state)
            }
        })
    }

    private fun initUi() {
        recycler = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)

        recycler.adapter = adapter
        adapter
                .addViewType<Venue, VenueListWidget>(Venue::class.java)
                .addViewCreator { viewGroup -> VenueListWidget(viewGroup.context) }
                .addViewBinder { widget, item -> widget.bind(item) }
                .commit()

        searchBar = findViewById(R.id.search_bar)
        searchBar.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.presenter.searchVenues(textView.text.toString())

            }
            true
        }

        loader = findViewById(R.id.loader)
    }

    private fun onNewState(state: VenueListViewState) {
        when (state) {
            is VenueListViewState.Loading -> showLoading()
            is Error -> showError()
            is VenueListViewState.Empty -> showEmpty()
            is VenueListViewState.Data -> showData(state.items)
            else -> throw IllegalStateException("unknown state")
        }
    }

    private fun showData(items: List<Venue>) {
        loader.hide()
        adapter.addAll(items)
        adapter.notifyDataSetChanged()
    }

    private fun showEmpty() {
//        loader.showError {  }

    }

    private fun showError() {
        loader.showNetworkError()

    }

    private fun showLoading() {
        loader.showLoading()

    }


}
