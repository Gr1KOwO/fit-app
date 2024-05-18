package com.example.flexify.data.Storage.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.flexify.data.dbModel.ExerciseType

@Dao
interface ExerciseTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseType(exerciseTypes:List<ExerciseType>)
    @Delete
    suspend fun deleteExerciseType(exerciseTypes:ExerciseType)
    @Update
    suspend fun updateExerciseType(exerciseTypes:ExerciseType)
    @Query("Select * from ExerciseType")
    suspend fun getExerciseType():List<ExerciseType>
}