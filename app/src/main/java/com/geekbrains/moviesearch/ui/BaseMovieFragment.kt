package com.geekbrains.moviesearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.MovieListFilter
import com.geekbrains.moviesearch.data.vo.Movie

abstract class BaseMovieFragment<T : Any> : Fragment(), OnMovieItemClickListener {

    protected val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder> by lazy {
        recyclerAdapter()
    }
    protected val viewModel: BaseViewModel<T> by lazy {
        viewModel() as BaseViewModel<T>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return fragmentViewProvider(inflater, container, savedInstanceState)
    }

    abstract fun fragmentViewProvider(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View

    abstract fun viewModel(): ViewModel
    abstract fun recyclerLayoutManagerProvider(): RecyclerView.LayoutManager
    abstract fun toDetailsAction(): Int

    var showAdult: Boolean = false

    open fun movieListFilter(): MovieListFilter = MovieListFilter.All
    open fun recyclerItemLayoutId(): Int = R.layout.movie_cardview_item

    open fun recyclerAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> =
        MoviesAdapter(
            recyclerItemLayoutId(),
            this,
        ) as RecyclerView.Adapter<RecyclerView.ViewHolder>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.recycler_view)?.let {
            it.layoutManager = recyclerLayoutManagerProvider()
            it.adapter = adapter
        }
        getShowAdultOption()
        viewModel.getLoadedData(showAdult).observe(viewLifecycleOwner, { state ->
            activity?.findViewById<View>(R.id.mainFragmentLoadingLayout)?.showIf {
                state is LoadingState.Loading
            }
            showLoadedState(state)
        })
    }

    private fun getShowAdultOption() {
        showAdult = PreferenceManager.getDefaultSharedPreferences(context?.applicationContext)
            .getBoolean(getString(R.string.pref_adult_key), false)
    }

    override fun onMovieClicked(movie: Movie) {
        Bundle().also {
            it.putInt("movieKey", movie.id)
            NavHostFragment.findNavController(this)
                .navigate(toDetailsAction(), it)
        }
    }

    abstract fun showLoadedState(state: LoadingState<T>)

    override fun onMovieIconsClicked(movie: Movie) {
        viewModel.updateMovie(movie)
    }
}