package com.projectbox.filem.service

import android.content.Context
import com.projectbox.filem.util.NetworkUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by adinugroho
 */
class ConnectivityInterceptor(private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetworkUtil.isOnline(context)) {
            throw NoConnectivityException()
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}

class NoConnectivityException: IOException() {
    override val message: String?
        get() = "No connectivity"
}