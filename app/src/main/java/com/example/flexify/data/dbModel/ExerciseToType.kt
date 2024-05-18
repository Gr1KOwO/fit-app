package com.example.flexify.data.dbModel

import androidx.room.Entity
import androidx.room.ForeignKey
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["exerciseId", "exerciseTypeId"],
        foreignKeys = [
    ForeignKey(entity = Exercise::class, parentColumns = ["id"], childColumns = ["exerciseId"]),
    ForeignKey(entity = ExerciseType::class, parentColumns = ["id"], childColumns = ["exerciseTypeId"])
])
data class ExerciseToType(
    @SerializedName("exerciseId")val exerciseId:Int,
    @SerializedName("exerciseTypeId")val exerciseTypeId:Int
)
