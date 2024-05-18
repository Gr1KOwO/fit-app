package com.example.flexify.data.Storage.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.flexify.data.dbModel.Exercise

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercises:List<Exercise>)
    @Delete
    suspend fun deleteExercise(exercise: Exercise)
    @Update
    suspend fun updateExerciseType(exercise: Exercise)
    @Query("Select * from Exercise")
    suspend fun getExercise():List<Exercise>
}