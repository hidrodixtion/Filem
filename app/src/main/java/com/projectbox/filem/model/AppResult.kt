package com.projectbox.filem.model

/**
 * Created by adinugroho
 */
sealed class AppResult<out T: Any> {
    data class Success<out T: Any>(val data: T): AppResult<T>()
    data class Failure(val exception: Exception): AppResult<Nothing>()
}