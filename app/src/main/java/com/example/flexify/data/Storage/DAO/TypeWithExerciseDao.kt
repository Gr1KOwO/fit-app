package com.example.flexify.data.Storage.DAO

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.flexify.data.dbModel.TypeWithExercise

@Dao
interface TypeWithExerciseDao {
    @Transaction
    @Query("Select * From ExerciseType")
    suspend fun getTypeWithExerciseDao():List<TypeWithExercise>
}