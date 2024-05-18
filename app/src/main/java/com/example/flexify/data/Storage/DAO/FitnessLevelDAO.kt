package com.example.flexify.data.Storage.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.flexify.data.dbModel.FitnessLevel

@Dao
interface FitnessLevelDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savedata(list:List<FitnessLevel>)
    @Query("Select * from FitnessLevel")
    suspend fun getLevelFitness():List<FitnessLevel>
    @Update
    suspend fun updateLevelFitness(levelFitness:FitnessLevel)
    @Delete
    suspend fun delLevelFitness(levelFitness:FitnessLevel)
}