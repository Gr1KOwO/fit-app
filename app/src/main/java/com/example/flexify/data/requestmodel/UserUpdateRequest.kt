package com.example.flexify.data.requestmodel

import com.google.gson.annotations.SerializedName

data class UserUpdateRequest(
    @SerializedName("fullName")val fullName: String? = null,
    @SerializedName("password")val password:String? = null,
    @SerializedName("age")val age: Int? = null,
    @SerializedName("weight")val weight: Float? = null,
    @SerializedName("height")val height: Float? = null,
    @SerializedName("sex")val sex: String? = null
)
