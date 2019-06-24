package com.projectbox.filem.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.projectbox.filem.BuildConfig
import com.projectbox.filem.R
import com.projectbox.filem.extension.convertToReadableDate
import com.projectbox.filem.model.Movie
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Movie Detail"
    }

    private fun fillFields() {
        if (!intent.hasExtra("data"))
            return

        val movie = intent.getParcelableExtra<Movie>("data")
        txt_title.text = movie.title
        txt_date.text = movie.releaseDate.convertToReadableDate()
        txt_overview.text = movie.overview
        txt_score.text = "${(movie.vote * 10).roundToInt()}%"

        val posterUrl = "${BuildConfig.BIG_IMAGE_URL}${movie.poster}"
        Glide.with(this).load(posterUrl).into(img_poster)
    }
}
