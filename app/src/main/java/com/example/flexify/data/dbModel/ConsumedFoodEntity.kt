package com.example.flexify.data.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

@Entity
data class ConsumedFoodEntity(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")val id: Int,
    @SerializedName("date")val date: LocalDate,
    @SerializedName("userId")val userId: Int,
    @SerializedName("foodId")val foodId: Int?,
    @SerializedName("dishId")val dishId: Int?,
    @SerializedName("quantity")val quantity: Int,
    @SerializedName("calories")val calories: Float
)
