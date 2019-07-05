package com.projectbox.filem.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.projectbox.filem.BuildConfig
import com.projectbox.filem.R
import com.projectbox.filem.extension.convertToReadableDate
import com.projectbox.filem.model.MovieTvShow
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlin.math.roundToInt

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        initUi()
        fillFields()
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
    }

    private fun fillFields() {
        if (!intent.hasExtra("data"))
            return

        val data = intent.getParcelableExtra<MovieTvShow>("data")
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
}
