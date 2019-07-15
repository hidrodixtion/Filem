package com.projectbox.filem

import android.content.Context
import com.projectbox.filem.db.AppDB
import com.projectbox.filem.repository.MovieRepository
import com.projectbox.filem.service.ConnectivityInterceptor
import com.projectbox.filem.service.IService
import com.projectbox.filem.viewmodel.MovieDetailVM
import com.projectbox.filem.viewmodel.MovieListVM
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by adinugroho
 */
class KoinModules {
    val appModules = module {
        single { createLoggingInterceptor() }
        single { createRetrofitClient(get(), getProperty("API_KEY"), get()) }
        single { createService(get(), getProperty("BASE_URL")) }
        single { AppDB.getDatabase(get()) }

        factory { get<AppDB>().favoriteDao() }

        factory { MovieRepository(get(), get()) }

        viewModel { MovieListVM(get()) }
        viewModel { MovieDetailVM(get()) }
    }

    private fun createLoggingInterceptor(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().addInterceptor(logging)
            .connectTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
    }

    private fun createRetrofitClient(exisitingBuilder: OkHttpClient.Builder, apiKey: String, context: Context): OkHttpClient {
        exisitingBuilder.addInterceptor(ConnectivityInterceptor(context))

        exisitingBuilder.addInterceptor { chain ->
            val original = chain.request()
            val originalUrl = original.url()

            val url = originalUrl.newBuilder()
                .addQueryParameter("api_key", apiKey)
                .addQueryParameter("language", "en-US")
                .addQueryParameter("year", "2019")
                .build()
            val requestBuilder = original.newBuilder().url(url)
            val request = requestBuilder.build()

            return@addInterceptor chain.proceed(request)
        }

        // add cache header
        val myCache = Cache(context.cacheDir, (5 * 1024 * 1024).toLong())
        exisitingBuilder.cache(myCache).addInterceptor { chain ->
            val original = chain.request()
            // create a cache header
            val cacheHeaderRequest = original.newBuilder().header("Cache-Control", "public, max-age=" + 10).build()
            return@addInterceptor chain.proceed(cacheHeaderRequest)
        }

        return exisitingBuilder.build()
    }

    private fun createService(client: OkHttpClient, baseUrl: String): IService {
        val retrofit = Retrofit.Builder().baseUrl(baseUrl).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(IService::class.java)
    }
}