package com.example.flexify.data.ResponseModel

import com.google.gson.annotations.SerializedName

data class ResponseResult(
    @SerializedName("success")val success:Boolean,
    @SerializedName("message")val message:String
)
