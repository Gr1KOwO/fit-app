package com.example.flexify.data.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Food(
    @PrimaryKey
    @SerializedName("id")val id: Int,
    @SerializedName("name")val name: String,
    @SerializedName("foodTypeId")val foodTypeId: Int,
    @SerializedName("calories")val calories: Float
)
