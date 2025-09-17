package com.example.lda.server

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

data class GeminiRequest(
    val contents: List<Map<String, Any>>
)

data class GeminiResponse(
    val candidates: List<Map<String, Any>>?
)

interface GeminiApi {
    @Headers(
        "Content-Type: application/json",
        "x-goog-api-key: AIzaSyDhIShAtj_E7piEdLZ5EslchH3omRh_l_E"
    )
    @POST("v1beta/models/gemini-1.5-flash:generateContent")
    fun validateName(@Body body: GeminiRequest): Call<GeminiResponse>
}
