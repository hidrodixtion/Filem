package com.projectbox.filem.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectbox.filem.model.AppResult
import com.projectbox.filem.model.Cast
import com.projectbox.filem.model.ListType
import com.projectbox.filem.repository.MovieRepository
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Created by adinugroho
 */
class MovieDetailVM(private val repo: MovieRepository) : ViewModel() {
    val castList = MutableLiveData<AppResult<List<Cast>>>()

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
}