package com.example.flexify.data.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class FoodType(
    @PrimaryKey
    @SerializedName("id") val id: Int,
    @SerializedName("name")val name: String
)
