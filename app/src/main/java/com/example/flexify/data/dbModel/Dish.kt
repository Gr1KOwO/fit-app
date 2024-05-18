package com.example.flexify.data.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Dish(
    @PrimaryKey
    @SerializedName("id")val id: Int,
    @SerializedName("name")val name: String,
    @SerializedName("description")val description: String,
    @SerializedName("recipe")val recipe: String,
    @SerializedName("videoLink")val videoLink: String,
    @SerializedName("calories")val calories: Float
)
