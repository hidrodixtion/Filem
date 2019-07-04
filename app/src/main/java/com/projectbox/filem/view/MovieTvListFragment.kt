package com.projectbox.filem.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectbox.filem.R
import com.projectbox.filem.adapter.MovieTvAdapter
import com.projectbox.filem.model.ListType
import com.projectbox.filem.viewmodel.MovieListVM
import kotlinx.android.synthetic.main.fragment_movie_list.*
import org.jetbrains.anko.startActivity
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 *
 */
open class MovieTvListFragment : Fragment() {

    companion object {
        const val LIST_TYPE = "list_type_key"

        fun getInstance(listType: ListType): MovieTvListFragment {
            val bundle = Bundle()
            bundle.putString(LIST_TYPE, listType.name)
            val fragment = MovieTvListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val vm by viewModel<MovieListVM>()

    lateinit var adapter: MovieTvAdapter
    var listType = ListType.MOVIE

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArguments()
        initList()
        initListeners()
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun initArguments() {
        arguments?.let {
            if (it.containsKey(LIST_TYPE))
                listType = ListType.valueOf(it.getString(LIST_TYPE))
        }
    }

    private fun initList() {
        adapter = MovieTvAdapter(emptyList()) { item ->
            activity?.startActivity<MovieDetailActivity>("data" to item)
        }

        recycler_view.layoutManager = LinearLayoutManager(this.activity)
        recycler_view.adapter = adapter
    }

    private fun initListeners() {
        vm.itemList.observe(this, Observer { items ->
            loading_animation.pauseAnimation()
            loading_animation.visibility = View.GONE
            recycler_view.visibility = View.VISIBLE
            adapter.update(items)
        })

        when(listType) {
            ListType.MOVIE -> vm.getMovies()
            ListType.TVSHOW -> vm.getTvShow()
        }
    }
}
