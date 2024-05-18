package com.example.flexify.data.Storage.DAO

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.flexify.data.dbModel.ExerciseWithType

@Dao
interface ExerciseWithTypeDao {
    @Transaction
    @Query("Select * From Exercise")
    suspend fun getExerciseWithType():List<ExerciseWithType>
}