package com.example.flexify.data.dbModel

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseWithType(
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(ExerciseToType::class,
            parentColumn = "exerciseId",
            entityColumn = "exerciseTypeId")
    )
    val exercisesType: List<ExerciseType>
): Parcelable
