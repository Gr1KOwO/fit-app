package com.example.flexify.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.flexify.data.Converter.LocalDateAdapter
import com.example.flexify.data.FitApi
import com.example.flexify.data.interceptors.HeaderInterceptor
import com.example.flexify.data.repository.PreferenceStorage
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

@Module
class NetworkModule {

    companion object {
        private const val BASE_ENDPOINT = "https://ktor-fitness-server.onrender.com/fitness-api/v1/"
    }
    @Provides
    fun provideOkHttp(
        preferenceStorage: PreferenceStorage,
        context: Context,
    ) = OkHttpClient.Builder().apply {
        addInterceptor(HeaderInterceptor(preferenceStorage))
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        addInterceptor(loggingInterceptor)

        addInterceptor(ChuckerInterceptor.Builder(context)
            .alwaysReadResponseBody(true)
            .build())
   }
        .connectTimeout(120000L, TimeUnit.MILLISECONDS)
        .readTimeout(120000L, TimeUnit.MILLISECONDS)
        .writeTimeout(120000L, TimeUnit.MILLISECONDS)
        .build()

    @Provides
       fun provideRetrofit(
        httpClient: OkHttpClient,
        gson: Gson,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient)
        .build()
    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .create()
    }
    @Provides
    fun provideApiService(
        retrofit: Retrofit,
    ): FitApi {
        return retrofit.create(FitApi::class.java)
    }
}