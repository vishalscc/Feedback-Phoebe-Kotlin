package com.scc.shake.api

import com.google.gson.GsonBuilder
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object ApiClient {
    private const val BASE_URL = "http://192.168.0.111/best/Feedback-Phoebe/public/api/"

    private val okHttpClient: OkHttpClient? = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))

    private var retrofit: Retrofit? = null
        private get() {
            if (field == null) field = builder.build()
            return field
        }
    private var API_SERVICE_INSTANCE: ApiService? = null

    val apiService: ApiService?
        get() {
            if (API_SERVICE_INSTANCE == null) API_SERVICE_INSTANCE = retrofit!!.create(
                ApiService::class.java
            )
            return API_SERVICE_INSTANCE
        }

    fun cancelAllRequest() {
        if (okHttpClient != null) okHttpClient.dispatcher.cancelAll()
    }
}