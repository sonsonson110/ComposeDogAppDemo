package com.example.kotlindogapp.data.api

import com.example.kotlindogapp.common.constant.API_KEY
import com.example.kotlindogapp.data.model.DogApiModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DogApiService {
    @Headers("x-api-key: $API_KEY")
    @GET("images/search?limit=10")
    suspend fun getDogList(): List<DogApiModel>
}