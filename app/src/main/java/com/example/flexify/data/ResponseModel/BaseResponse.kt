package com.example.flexify.data.ResponseModel

import com.google.gson.annotations.SerializedName

class BaseResponse <T>(
    @SerializedName("data") val data: T,
)