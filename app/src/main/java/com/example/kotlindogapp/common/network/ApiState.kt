package com.example.kotlindogapp.common.network

sealed class ApiState{
    data object Loading : ApiState()
    class Failure(val e: Throwable) : ApiState()
    class Success<T>(val data: T) : ApiState()
    data object Empty : ApiState()
}