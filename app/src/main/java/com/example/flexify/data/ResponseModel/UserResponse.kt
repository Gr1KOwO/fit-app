package com.example.flexify.data.ResponseModel

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("userId")val userId:Int,
    @SerializedName("fullName")val fullName: String,
    @SerializedName("age")val age: Int,
    @SerializedName("weight")val weight: Float,
    @SerializedName("height")val height: Float,
    @SerializedName("sex")val sex: String,
)
