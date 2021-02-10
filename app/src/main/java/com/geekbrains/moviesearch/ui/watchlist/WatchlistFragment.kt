package com.geekbrains.moviesearch.ui.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.ui.BaseRecyclerFragment
import com.geekbrains.moviesearch.ui.MovieRecyclerViewAdapter
import com.geekbrains.moviesearch.ui.OnItemClickListener
import com.geekbrains.moviesearch.ui.SwipeToDeleteCallback
import com.geekbrains.moviesearch.vo.Movie
import kotlinx.android.synthetic.main.fragment_search_list.*
import kotlinx.android.synthetic.main.fragment_watchlist.*
import kotlinx.android.synthetic.main.fragment_watchlist.recycler_view


class WatchlistFragment : BaseRecyclerFragment(), OnItemClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recycler_view as RecyclerView)
    }

    override fun fragmentViewProvider(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_watchlist, parent, false)


    override fun recyclerAdapterProvider(): MovieRecyclerViewAdapter =
        MovieRecyclerViewAdapter(R.layout.movie_list_item, this)


    override fun recyclerLayoutManagerProvider(): RecyclerView.LayoutManager =
        LinearLayoutManager(context)


    override fun onItemClicked(movie: Movie?) {
        super.onItemClicked(movie)
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_nav_watchlist_to_detailsFragment)

    }
}