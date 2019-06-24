package com.projectbox.filem.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectbox.filem.model.Movie
import com.projectbox.filem.service.IService
import kotlinx.coroutines.launch

/**
 * Created by adinugroho
 */
class MovieListVM(private val service: IService) : ViewModel() {
    val movies: MutableLiveData<List<Movie>> = MutableLiveData()

    fun getMovies() {
        viewModelScope.launch {
            val response = service.getMovieList()
            movies.value = response.results
        }
    }
}