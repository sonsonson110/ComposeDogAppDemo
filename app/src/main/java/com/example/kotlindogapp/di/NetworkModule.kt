package com.example.kotlindogapp.di

import com.example.kotlindogapp.common.constant.BASE_URL
import com.example.kotlindogapp.data.api.DogApiService
import com.example.kotlindogapp.data.repository.DogRepository
import com.example.kotlindogapp.data.repository.DogRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val TIME_OUT: Long = 10

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val resp = chain.proceed(chain.request())
            // deal with the response code
            if (resp.code() == 200) {
                try {
                    val myJson = resp.peekBody(2048).string()
                    println(myJson)
                } catch (e: Exception) {
                    println("Error parse json from intercept...............")
                }
            } else {
                println(resp)
            }
            resp
        }.build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideDogApiService(retrofit: Retrofit): DogApiService = retrofit.create(DogApiService::class.java)
}