package com.example.flexify.data.Storage.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.flexify.data.dbModel.Statistics

@Dao
interface statisticDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStatistic(statistics: Statistics)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStatistics(statistics: List<Statistics>)

    @Query("Select * from Statistics")
    suspend fun getStatistics():List<Statistics>

    @Query("SELECT * FROM Statistics WHERE userId = :userId")
    suspend fun getStatisticsByUserId(userId: Int): List<Statistics>

    @Update
    suspend fun updateStatistics(statistics: Statistics)

    @Delete
    suspend fun deleteStatistics(statistics: Statistics)
}