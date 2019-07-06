package com.projectbox.filem.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.projectbox.filem.BuildConfig
import com.projectbox.filem.R
import com.projectbox.filem.adapter.CastAdapter
import com.projectbox.filem.extension.convertToReadableDate
import com.projectbox.filem.model.AppResult
import com.projectbox.filem.model.MovieTvShow
import com.projectbox.filem.viewmodel.MovieDetailVM
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import kotlin.math.roundToInt

class MovieDetailActivity : AppCompatActivity() {

    private val vm by viewModel<MovieDetailVM>()

    lateinit var adapter: CastAdapter
    lateinit var data: MovieTvShow

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
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        collapsing_toolbar.title = resources.getString(R.string.title_detail_movie)
        collapsing_toolbar.setCollapsedTitleTextColor(ContextCompat.getColor(this, android.R.color.white))
        collapsing_toolbar.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent))

        recycler_view_cast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view_cast.setHasFixedSize(true)

        adapter = CastAdapter(emptyList())
        recycler_view_cast.adapter = adapter
    }

    private fun fillFields() {
        if (!intent.hasExtra("data"))
            return

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

        if (::data.isInitialized)
            vm.getCasts(data.id)
    }
}
