package com.projectbox.filem.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectbox.filem.model.Movie
import com.projectbox.filem.repository.MovieRepository
import kotlinx.coroutines.launch

/**
 * Created by adinugroho
 */
class MovieListVM(private val repo: MovieRepository) : ViewModel() {
    val movies: MutableLiveData<List<Movie>> = MutableLiveData()

    fun getMovies() {
        viewModelScope.launch {
            val result = repo.getMovieList()
            movies.value = result
        }
    }
}