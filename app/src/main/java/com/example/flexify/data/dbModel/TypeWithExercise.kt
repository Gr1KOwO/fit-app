package com.example.flexify.data.dbModel

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

data class TypeWithExercise(
    @Embedded val exerciseType: ExerciseType,
    @Relation(
        parentColumn = "id",  // This should match the primary key of the ExerciseType entity
        entityColumn = "id",  // This should match the primary key of the Exercise entity
        associateBy = Junction(ExerciseToType::class,
            parentColumn = "exerciseTypeId",
            entityColumn = "exerciseId")
    )
    val exercises: List<Exercise>
)
