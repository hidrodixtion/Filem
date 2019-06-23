package com.projectbox.filem.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.projectbox.filem.R
import com.projectbox.filem.model.Movie
import com.projectbox.filem.viewholder.MovieMenuVH

/**
 * Created by adinugroho
 */
class MovieListAdapter(var movies: List<Movie>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.item_movie_list, parent, false)
        val vh = MovieMenuVH(view)
        vh.bind(getItem(position))
        return view
    }

    override fun getItem(position: Int): Movie = movies[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = movies.size

    fun update(movies: List<Movie>) {
        this.movies = movies
        this.notifyDataSetChanged()
    }
}