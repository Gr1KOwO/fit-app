package com.example.flexify.data.ResponseModel

import androidx.room.PrimaryKey
import com.example.flexify.data.dbModel.Statistics
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class ResponseModelStatistics (
    val list: List<Statistics>
)