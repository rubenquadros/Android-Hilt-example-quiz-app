package com.ruben.funed.injection

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ruben.funed.BuildConfig
import com.ruben.funed.remote.ApiConstants
import com.ruben.funed.remote.retrofit.RetrofitApi
import com.ruben.funed.remote.retrofit.RetrofitInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by ruben.quadros on 19/06/21.
 **/
@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    fun provideRetrofitInterceptor() = RetrofitInterceptor()

    @Provides
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    fun provideCache(context: Context): Cache {
        val cacheSize = (10 * 1024 * 1024).toLong() //10 MB
        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, cacheSize)
    }

    @Provides
    fun provideOkHttpClient(cache: Cache, retrofitInterceptor: RetrofitInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        if(BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addNetworkInterceptor(loggingInterceptor)
        }
        okHttpClient.addInterceptor(retrofitInterceptor)
        okHttpClient.cache(cache)
        okHttpClient.readTimeout(ApiConstants.TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClient.writeTimeout(ApiConstants.TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClient.connectTimeout(ApiConstants.TIMEOUT.toLong(), TimeUnit.SECONDS)
        return okHttpClient.build()
    }

    @Provides
    fun provideRetrofitApi(okHttpClient: OkHttpClient, gson: Gson): RetrofitApi =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
            .create(RetrofitApi::class.java)

}