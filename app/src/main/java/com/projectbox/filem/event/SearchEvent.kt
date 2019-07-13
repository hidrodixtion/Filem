package com.projectbox.filem.event

/**
 * Created by adinugroho
 */
sealed class SearchEvent<out T: Any> {
    data class Started(val opened: Boolean = true): SearchEvent<Boolean>()
    data class Query(val text: String): SearchEvent<String>()
    data class Closed(val closed: Boolean = true): SearchEvent<Boolean>()
}