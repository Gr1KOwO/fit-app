package com.example.flexify.data.requestmodel

import com.google.gson.annotations.SerializedName


data class RequestLogin(
    @SerializedName("email") val email: String,
    @SerializedName("password")val password: String,
    @SerializedName("fullName")val fullName: String,
    @SerializedName("age")val age: Int,
    @SerializedName("weight")val weight: Float,
    @SerializedName("height")val height: Float,
    @SerializedName("sex")val sex: String,
)
