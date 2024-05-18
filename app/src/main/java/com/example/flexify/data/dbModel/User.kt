package com.example.flexify.data.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @PrimaryKey
    @SerializedName("userId")val userId:Int,
    @SerializedName("fullName")val fullName: String,
    @SerializedName("age")val age: Int,
    @SerializedName("weight")val weight: Float,
    @SerializedName("height")val height: Float,
    @SerializedName("sex")val sex: String,
)
