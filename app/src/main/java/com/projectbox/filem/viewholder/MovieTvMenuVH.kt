package com.projectbox.filem.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.projectbox.filem.BuildConfig
import com.projectbox.filem.extension.convertToReadableDate
import com.projectbox.filem.model.MovieTvShow
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie_list.*

/**
 * Created by adinugroho
 */
class MovieTvMenuVH(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(item: MovieTvShow) {
        item.movieTitle?.let {
            txt_name.text = it
        }

        item.showTitle?.let {
            txt_name.text = it
        }

        // for movie, use release date
        item.releaseDate?.let {
            txt_date.text = it.convertToReadableDate()
        }

        // for tvshow, use first air date
        item.firstAirDate?.let {
            txt_date.text = it.convertToReadableDate()
        }


        val imageUrl = "${BuildConfig.SMALL_IMAGE_URL}${item.poster}"
        Glide.with(containerView).load(imageUrl).into(img_poster)
    }
}