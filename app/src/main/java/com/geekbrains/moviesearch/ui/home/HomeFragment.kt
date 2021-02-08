package com.geekbrains.moviesearch.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.ui.DummyContent
import com.geekbrains.moviesearch.ui.MovieRecyclerViewAdapter
import com.geekbrains.moviesearch.ui.OnItemClickListener
import com.geekbrains.moviesearch.vo.Movie

class HomeFragment : Fragment(), OnItemClickListener {

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        // Set the adapter
        view.findViewById<RecyclerView>(R.id.recycler_view)?.let {
            it.layoutManager = GridLayoutManager(context, 3)
            it.adapter =
                MovieRecyclerViewAdapter(DummyContent.ITEMS, R.layout.movie_cardview_item, this)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onItemClicked(responce: Movie?) {
        NavHostFragment.findNavController(this).navigate(R.id.action_nav_home_to_detailsFragment)
    }
}