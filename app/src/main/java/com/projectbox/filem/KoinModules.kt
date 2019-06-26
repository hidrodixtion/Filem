package com.projectbox.filem

import com.projectbox.filem.repository.MovieRepository
import com.projectbox.filem.service.IService
import com.projectbox.filem.viewmodel.MovieListVM
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by adinugroho
 */
class KoinModules {
    val appModules = module {
        single { createLoggingInterceptor() }
        single { createRetrofitClient(get(), getProperty("API_KEY")) }
        single { createService(get(), getProperty("BASE_URL")) }

        factory { MovieRepository(get()) }

        viewModel { MovieListVM(get()) }
    }

    private fun createLoggingInterceptor(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().addInterceptor(logging)
            .connectTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
    }

    private fun createRetrofitClient(exisitingBuilder: OkHttpClient.Builder, apiKey: String): OkHttpClient {
        exisitingBuilder.addInterceptor { chain ->
            val original = chain.request()
            val originalUrl = original.url()

            val url = originalUrl.newBuilder().addQueryParameter("api_key", apiKey).build()
            val requestBuilder = original.newBuilder().url(url)

            val request = requestBuilder.build()
            return@addInterceptor chain.proceed(request)
        }
        return exisitingBuilder.build()
    }

    private fun createService(client: OkHttpClient, baseUrl: String): IService {
        val retrofit = Retrofit.Builder().baseUrl(baseUrl).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(IService::class.java)
    }
}