package com.example.flexify.data.requestmodel

import com.google.gson.annotations.SerializedName

data class RequestSignIn(
    @SerializedName("email")val email:String,
    @SerializedName("password") val password:String,
)
