package com.projectbox.filem.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectbox.filem.model.MovieTvShow
import com.projectbox.filem.repository.MovieRepository
import kotlinx.coroutines.launch

/**
 * Created by adinugroho
 */
class MovieListVM(private val repo: MovieRepository) : ViewModel() {
    val itemList: MutableLiveData<List<MovieTvShow>> = MutableLiveData()

    fun getMovies() {
        viewModelScope.launch {
            val result = repo.getMovieList()
            itemList.value = result
        }
    }

    fun getTvShow() {
        viewModelScope.launch {
            val result = repo.getTvShowList()
            itemList.value = result
        }
    }
}