package com.projectbox.filem.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.projectbox.filem.R
import com.projectbox.filem.adapter.MovieListAdapter
import com.projectbox.filem.viewmodel.MovieListVM
import kotlinx.android.synthetic.main.fragment_movie_list.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 *
 */
class MovieListFragment : Fragment() {

    val vm by viewModel<MovieListVM>()

    lateinit var adapter: MovieListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init UI
        initList()
        initListeners()
    }

    private fun initList() {
        adapter = MovieListAdapter(emptyList())
        list_view.adapter = adapter
    }

    private fun initListeners() {
        vm.movies.observe(this, Observer { movies ->
            adapter.update(movies)
        })

        vm.getMovies(28)
    }
}