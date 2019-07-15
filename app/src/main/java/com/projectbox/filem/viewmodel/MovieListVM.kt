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
    val itemList = MutableLiveData<AppResult<List<MovieTvShow>>>()
    val searchItemList = MutableLiveData<AppResult<List<MovieTvShow>>>()

    fun getMovies(isFavorite: Boolean = false) {
        viewModelScope.launch {
            try {
                val result = if (isFavorite) repo.getFavoriteMovieList() else repo.getMovieList()
                itemList.value = AppResult.Success(result)
            } catch (e: Exception) {
                itemList.value = AppResult.Failure(e)
            }
        }
    }

    fun getTvShow(isFavorite: Boolean = false) {
        viewModelScope.launch {
            try {
                val result = if (isFavorite) repo.getFavoriteTvList() else repo.getTvShowList()
                itemList.value = AppResult.Success(result)
            } catch (e: Exception) {
                itemList.value = AppResult.Failure(e)
            }
        }
    }

    fun searchMovie(query: String, isFavorite: Boolean = false) {
        viewModelScope.launch {
            try {
                val result = if (isFavorite) repo.searchFavoriteMovie(query) else repo.searchMovie(query)
                searchItemList.value = AppResult.Success(result)
            } catch (e: Exception) {
                searchItemList.value = AppResult.Failure(e)
            }
        }
    }

    fun searchTvShow(query: String, isFavorite: Boolean = false) {
        viewModelScope.launch {
            try {
                val result = if (isFavorite) repo.searchFavoriteTvShow(query) else repo.searchTvShow(query)
                searchItemList.value = AppResult.Success(result)
            } catch (e: Exception) {
                searchItemList.value = AppResult.Failure(e)
            }
        }
    }
}