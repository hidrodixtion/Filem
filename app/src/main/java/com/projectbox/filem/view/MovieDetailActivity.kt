package com.projectbox.filem.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.projectbox.filem.BuildConfig
import com.projectbox.filem.R
import com.projectbox.filem.adapter.CastAdapter
import com.projectbox.filem.extension.convertToReadableDate
import com.projectbox.filem.model.AppResult
import com.projectbox.filem.model.ListType
import com.projectbox.filem.model.MovieTvShow
import com.projectbox.filem.viewmodel.MovieDetailVM
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import kotlin.math.roundToInt

class MovieDetailActivity : AppCompatActivity() {

    private val vm by viewModel<MovieDetailVM>()

    private lateinit var adapter: CastAdapter
    private lateinit var data: MovieTvShow
    private var type: ListType = ListType.MOVIE
    private var isFavorite: Boolean = false
    private var isRunningFirstTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        initUi()
        fillFields()
        getMoreData()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun initUi() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_back)
            setDisplayHomeAsUpEnabled(true)
        }

        collapsing_toolbar.apply {
            title = resources.getString(R.string.title_detail_movie)
            setCollapsedTitleTextColor(ContextCompat.getColor(this@MovieDetailActivity, android.R.color.white))
            setExpandedTitleColor(ContextCompat.getColor(this@MovieDetailActivity, android.R.color.transparent))
        }

        adapter = CastAdapter(emptyList())

        recycler_view_cast.apply {
            layoutManager = LinearLayoutManager(this@MovieDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = adapter
        }

        fab_favorite.setOnClickListener {
            if (isFavorite) {
                vm.removeFromFavorite(data)
            } else {
                vm.setAsFavorite(data)
            }
        }
    }

    private fun fillFields() {
        if (!intent.hasExtra("data"))
            return

        if (intent.hasExtra("type"))
            type = ListType.valueOf(intent.getStringExtra("type"))

        data = intent.getParcelableExtra("data")
        data.movieTitle?.let {
            txt_title.text = it
        }

        data.showTitle?.let {
            txt_title.text = it
        }

        data.releaseDate?.let {
            txt_date.text = it.convertToReadableDate()
        }

        data.firstAirDate?.let {
            txt_label_date.text = resources.getString(R.string.first_air_date)
            txt_date.text = it.convertToReadableDate()
            supportActionBar?.title = resources.getString(R.string.title_detail_tv)
        }

        txt_overview.text = data.overview
        txt_score.text = "${(data.vote * 10).roundToInt()}%"

        val posterUrl = "${BuildConfig.BIG_IMAGE_URL}${data.poster}"
        Glide.with(this).load(posterUrl).into(img_poster)
        Glide.with(this).load(posterUrl).into(img_header)
    }

    private fun getMoreData() {
        vm.castList.observe(this, Observer { result ->
            preloader.pauseAnimation()
            preloader.visibility = View.GONE

            when (result) {
                is AppResult.Success -> {
                    adapter.update(result.data)
                }
                is AppResult.Failure -> {
                    Timber.e(result.exception.localizedMessage)
                }
            }
        })

        vm.isFavorite.observe(this, Observer { faved ->
            if (faved) {
                fab_favorite.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
                if (!isRunningFirstTime)
                    Snackbar.make(constraint, resources.getString(R.string.added_to_fave), Snackbar.LENGTH_SHORT).show()
            } else {
                fab_favorite.icon = ContextCompat.getDrawable(this, R.drawable.ic_unfavorite)
                if (!isRunningFirstTime)
                    Snackbar.make(constraint, resources.getString(R.string.removed_from_fave), Snackbar.LENGTH_SHORT).show()
            }

            isRunningFirstTime = false
            isFavorite = faved
        })

        if (::data.isInitialized) {
            vm.getCast(type, data.id)
            vm.isFavorited(data.id)
        }
    }
}
