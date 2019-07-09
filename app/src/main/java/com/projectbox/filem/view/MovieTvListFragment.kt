package com.projectbox.filem.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectbox.filem.R
import com.projectbox.filem.adapter.MovieTvAdapter
import com.projectbox.filem.model.AppResult
import com.projectbox.filem.model.ListType
import com.projectbox.filem.service.NoConnectivityException
import com.projectbox.filem.viewmodel.MovieListVM
import kotlinx.android.synthetic.main.exception_info.*
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
        const val IS_FAVORITE = "is_favorite_key"

        fun getInstance(listType: ListType, isFavorite: Boolean = false): MovieTvListFragment {
            val bundle = Bundle()
            bundle.putString(LIST_TYPE, listType.name)
            bundle.putBoolean(IS_FAVORITE, isFavorite)
            val fragment = MovieTvListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val vm by viewModel<MovieListVM>()

    private lateinit var adapter: MovieTvAdapter
    private var listType = ListType.MOVIE
    private var isFavorite = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArguments()
        initList()
        initListeners()

        if (savedInstanceState == null)
            getData()
    }

    override fun onResume() {
        super.onResume()
        if (isFavorite)
            getData()
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun initArguments() {
        arguments?.let {
            if (it.containsKey(LIST_TYPE)) {
                listType = ListType.valueOf(it.getString(LIST_TYPE))
                isFavorite = it.getBoolean(IS_FAVORITE)
            }
        }
    }

    private fun initList() {
        adapter = MovieTvAdapter(emptyList()) { item ->
            activity?.startActivity<MovieDetailActivity>("data" to item, "type" to listType.name)
        }

        recycler_view.layoutManager = LinearLayoutManager(this.activity)
        recycler_view.adapter = adapter
    }

    private fun initListeners() {
        vm.itemList.observe(this, Observer { result ->
            loading_animation.pauseAnimation()
            loading_animation.visibility = View.GONE
            recycler_view.visibility = View.VISIBLE
            container_info.visibility = View.GONE

            when(result) {
                is AppResult.Success -> adapter.update(result.data)
                is AppResult.Failure -> displayFailureInfo(result.exception)
            }
        })
    }

    private fun getData() {
        container_info.visibility = View.GONE
        loading_animation.resumeAnimation()
        loading_animation.visibility = View.VISIBLE

        when(listType) {
            ListType.MOVIE -> vm.getMovies(isFavorite)
            ListType.TVSHOW -> vm.getTvShow(isFavorite)
        }
    }

    private fun displayFailureInfo(ex: Exception) {
        container_info.visibility = View.VISIBLE
        if (ex is NoConnectivityException) {
            txt_info.text = resources.getString(R.string.info_no_connection)
            icon_info.setImageDrawable(ContextCompat.getDrawable(this.context!!, R.drawable.ic_no_connection))
        } else {
            txt_info.text = resources.getString(R.string.info_problem)
            icon_info.setImageDrawable(ContextCompat.getDrawable(this.context!!, R.drawable.ic_sync_problem))
        }

        btn_info.setOnClickListener { getData() }
    }
}
