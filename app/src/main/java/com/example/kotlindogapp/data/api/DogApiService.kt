package com.example.kotlindogapp.data.api

import com.example.kotlindogapp.common.constant.API_KEY
import com.example.kotlindogapp.common.network.ApiState
import com.example.kotlindogapp.data.model.DogApiModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface DogApiService {
    @Headers("x-api-key: $API_KEY")
    @GET("images/search")
    suspend fun getDogList(
        @Query("limit") limit: Int = 5,
        @Query("has_breeds") hasBreeds: Int = 1
    ): List<DogApiModel>

    @Headers("x-api-key: $API_KEY")
    @GET("images/{dogId}")
    suspend fun getDogById(@Path("dogId") dogId: String): DogApiModel
}