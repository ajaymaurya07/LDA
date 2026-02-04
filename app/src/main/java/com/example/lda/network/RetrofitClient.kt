package com.example.lda.network

import com.example.lda.constent.Constent
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS) // Set the connection timeout
        .readTimeout(30, TimeUnit.SECONDS)    // Set the read timeout
        .writeTimeout(30, TimeUnit.SECONDS)   // Set the write timeout
        .build()

    private val retrofitClient: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(Constent.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
    }


    val apiCall : ApiMethod by lazy {
        retrofitClient.build().create(ApiMethod::class.java)
    }




}