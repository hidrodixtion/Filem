package com.projectbox.filem.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectbox.filem.model.AppResult
import com.projectbox.filem.model.MovieTvShow
import com.projectbox.filem.repository.MovieRepository
import kotlinx.coroutines.launch

/**
 * Created by adinugroho
 */
class MovieListVM(private val repo: MovieRepository) : ViewModel() {
    val itemList: MutableLiveData<AppResult<List<MovieTvShow>>> = MutableLiveData()

    fun getMovies() {
        viewModelScope.launch {
            try {
                val result = repo.getMovieList()
                itemList.value = AppResult.Success(result)
            } catch (e: Exception) {
                itemList.value = AppResult.Failure(e)
            }
        }
    }

    fun getTvShow() {
        viewModelScope.launch {
            try {
                val result = repo.getTvShowList()
                itemList.value = AppResult.Success(result)
            } catch (e: Exception) {
                itemList.value = AppResult.Failure(e)
            }
        }
    }
}