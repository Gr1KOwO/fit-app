package com.example.flexify.di.exception

import com.example.flexify.data.ResponseModel.ErrorBaseResponse
import com.google.gson.Gson
import retrofit2.HttpException

fun Exception.getError(): String? {
    return if (this is HttpException) {
        val errorBody = response()?.errorBody()?.string()
        Gson().fromJson(errorBody, ErrorBaseResponse::class.java).error.message
    } else {
        message
    }
}