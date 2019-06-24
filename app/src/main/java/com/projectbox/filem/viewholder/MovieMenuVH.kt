package com.projectbox.filem.viewholder

import android.view.View
import com.bumptech.glide.Glide
import com.projectbox.filem.BuildConfig
import com.projectbox.filem.extension.convertToReadableDate
import com.projectbox.filem.model.Movie
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie_list.*

/**
 * Created by adinugroho
 */
class MovieMenuVH(override val containerView: View) : LayoutContainer {
    fun bind(item: Movie) {
        txt_name.text = item.title
        txt_date.text = item.releaseDate.convertToReadableDate()

        val imageUrl = "${BuildConfig.SMALL_IMAGE_URL}${item.poster}"
        Glide.with(containerView).load(imageUrl).into(img_poster)
    }
}