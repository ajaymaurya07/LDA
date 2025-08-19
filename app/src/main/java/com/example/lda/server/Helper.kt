package com.example.lda.server

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GeminiService {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: GeminiApi = retrofit.create(GeminiApi::class.java)
}
