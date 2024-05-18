package com.example.flexify.data.ResponseModel

import com.google.gson.annotations.SerializedName

data class ResponseSignIn(
    @SerializedName("success")val success: Boolean,
    @SerializedName("token")val token:String,
    @SerializedName("user")val user:UserResponse,
)
