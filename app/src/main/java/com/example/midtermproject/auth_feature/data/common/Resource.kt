package com.example.midtermproject.auth_feature.data.common

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    class Error(val errorMessage: String) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}