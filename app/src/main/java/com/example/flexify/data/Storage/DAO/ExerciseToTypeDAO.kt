package com.example.flexify.data.Storage.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.flexify.data.dbModel.ExerciseToType

@Dao
interface ExerciseToTypeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseToType(list:List<ExerciseToType>)
    @Query("Select * From ExerciseToType")
    suspend fun getExerciseToType():List<ExerciseToType>
}