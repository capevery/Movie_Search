package com.geekbrains.moviesearch.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.data.MovieListFilter
import com.geekbrains.moviesearch.ui.BaseMovieFragment
import com.geekbrains.moviesearch.ui.MainViewModel
import com.geekbrains.moviesearch.ui.MoviesAdapter
import com.geekbrains.moviesearch.ui.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.fragment_search_list.*

class SearchFragment : BaseMovieFragment() {

    override fun fragmentViewProvider(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_search_list, parent, false)
    }

    override fun viewModel(): MainViewModel =
        ViewModelProvider(this).get(MainViewModel::class.java)

    override fun recyclerItemLayoutId(): Int = R.layout.movie_list_item

    override fun recyclerLayoutManagerProvider(): RecyclerView.LayoutManager =
        LinearLayoutManager(context)

    override fun movieListFilter(): MovieListFilter = MovieListFilter.All

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemTouchHelper =
            ItemTouchHelper(SwipeToDeleteCallback(adapter as MoviesAdapter))
        itemTouchHelper.attachToRecyclerView(recycler_view as RecyclerView)
    }

    override fun toDetailsAction(): Int = R.id.action_nav_search_to_detailsFragment
}