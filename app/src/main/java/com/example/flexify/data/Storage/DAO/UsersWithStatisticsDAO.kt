package com.example.flexify.data.Storage.DAO

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.flexify.data.dbModel.UsersWithStatistics

@Dao
interface UsersWithStatisticsDAO {
    @Transaction
    @Query("SELECT * FROM User")
    suspend fun getUsersWithStatistics(): List<UsersWithStatistics>
}