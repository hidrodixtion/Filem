package com.projectbox.filem.util

import androidx.test.espresso.idling.CountingIdlingResource

/**
 * Created by adinugroho
 */
object IdlingResourceUtil {
    val idleResource = CountingIdlingResource("GLOBAL")

    fun increment() = idleResource.increment()
    fun decrement() = idleResource.decrement()
}