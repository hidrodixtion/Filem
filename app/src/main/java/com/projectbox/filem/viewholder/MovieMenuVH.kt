package com.projectbox.filem.viewholder

import android.view.View
import com.projectbox.filem.model.Movie
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie_list.*

/**
 * Created by adinugroho
 */
class MovieMenuVH(override val containerView: View) : LayoutContainer {
    fun bind(item: Movie) {
        txt_name.text = item.title
    }
}