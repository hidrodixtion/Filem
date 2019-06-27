package com.projectbox.filem.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projectbox.filem.R
import com.projectbox.filem.model.MovieTvShow
import com.projectbox.filem.viewholder.MovieTvMenuVH

/**
 * Created by adinugroho
 */
class MovieTvAdapter(private var itemList: List<MovieTvShow>, private val onItemClicked: (MovieTvShow) -> Unit) : RecyclerView.Adapter<MovieTvMenuVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTvMenuVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_list, parent, false)
        return MovieTvMenuVH(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: MovieTvMenuVH, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem)
        holder.containerView.setOnClickListener {
            onItemClicked.invoke(currentItem)
        }
    }

    fun update(newList: List<MovieTvShow>) {
        itemList = newList
        notifyDataSetChanged()
    }
}