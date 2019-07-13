package com.projectbox.filem.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by adinugroho
 */
fun String.convertToReadableDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    var date = Date(0)
    if (this.isNotEmpty())
        date = sdf.parse(this)
    val convertSdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    return convertSdf.format(date)
}