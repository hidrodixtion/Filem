package com.projectbox.filem.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectbox.filem.model.AppResult
import com.projectbox.filem.model.Cast
import com.projectbox.filem.repository.MovieRepository
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Created by adinugroho
 */
class MovieDetailVM(private val repo: MovieRepository) : ViewModel() {
    val castList = MutableLiveData<AppResult<List<Cast>>>()

    fun getCasts(movieId: String) {
        viewModelScope.launch {
            try {
                val result = repo.getCast(movieId)
                castList.value = AppResult.Success(result)
            } catch (e: Exception) {
                castList.value = AppResult.Failure(e)
            }
        }
    }
}