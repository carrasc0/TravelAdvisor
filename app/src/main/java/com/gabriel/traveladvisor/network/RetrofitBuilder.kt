package com.gabriel.traveladvisor.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory


object RetrofitBuilder {

    private const val BASE_URL = "http://api.irishrail.ie/realtime/realtime.asmx/"

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private var httpClient = OkHttpClient.Builder().apply {
        addInterceptor(interceptor)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(httpClient.build())
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)

}