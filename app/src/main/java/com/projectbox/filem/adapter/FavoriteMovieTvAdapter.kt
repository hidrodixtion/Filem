package com.projectbox.filem.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.projectbox.filem.R
import com.projectbox.filem.model.MovieTvShow
import com.projectbox.filem.viewholder.MovieTvMenuVH

/**
 * Created by adinugroho
 */
class FavoriteMovieTvAdapter(private val onItemClicked: (MovieTvShow) -> Unit) : PagedListAdapter<MovieTvShow, MovieTvMenuVH>(DATA_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTvMenuVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_list, parent, false)
        return MovieTvMenuVH(view)
    }

    override fun onBindViewHolder(holder: MovieTvMenuVH, position: Int) {
        val currentItem = getItem(position)

        currentItem?.let {
            holder.bind(it)
            holder.containerView.setOnClickListener {
                onItemClicked.invoke(currentItem)
            }
        }
    }

    companion object {
        private val DATA_COMPARATOR = object: DiffUtil.ItemCallback<MovieTvShow>() {
            override fun areItemsTheSame(oldItem: MovieTvShow, newItem: MovieTvShow): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieTvShow, newItem: MovieTvShow): Boolean =
                oldItem.overview == newItem.overview
        }
    }
}