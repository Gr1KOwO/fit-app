package com.example.flexify.data.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.time.LocalDate


@Entity
data class Statistics(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("idStatistic")val idStatistic:Int,
    @SerializedName("date") val date: LocalDate,
    @SerializedName("userId")val userId: Int,
    @SerializedName("caloriesSpent")val caloriesSpent: Float,
    @SerializedName("caloriesConsumed")val caloriesConsumed: Float,
)
