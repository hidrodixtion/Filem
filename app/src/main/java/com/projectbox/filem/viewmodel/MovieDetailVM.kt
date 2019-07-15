package com.projectbox.filem.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectbox.filem.model.AppResult
import com.projectbox.filem.model.Cast
import com.projectbox.filem.model.ListType
import com.projectbox.filem.model.MovieTvShow
import com.projectbox.filem.repository.MovieRepository
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Created by adinugroho
 */
class MovieDetailVM(private val repo: MovieRepository) : ViewModel() {
    val castList = MutableLiveData<AppResult<List<Cast>>>()
    val isFavorite = MutableLiveData<Boolean>()
    val movieDetail = MutableLiveData<MovieTvShow>()

    fun getCast(type: ListType, id: String) {
        viewModelScope.launch {
            try {
                val result: List<Cast> = when (type) {
                    ListType.MOVIE -> repo.getMovieCast(id)
                    ListType.TVSHOW -> repo.getTvCast(id)
                }
                castList.value = AppResult.Success(result)
            } catch (e: Exception) {
                castList.value = AppResult.Failure(e)
            }
        }
    }

    fun getDetail(id: String) {
        viewModelScope.launch {
            val movie = repo.getMovieDetail(id)
            movieDetail.value = movie
        }
    }

    fun setAsFavorite(data: MovieTvShow) {
        viewModelScope.launch {
            repo.addToFavorite(data)
            isFavorite.value = true
        }
    }

    fun removeFromFavorite(data: MovieTvShow) {
        viewModelScope.launch {
            repo.removeFromFavorite(data)
            isFavorite.value = false
        }
    }

    fun isFavorited(id: String) {
        viewModelScope.launch {
            val result = repo.isFavorite(id)
            isFavorite.value = result
        }
    }
}