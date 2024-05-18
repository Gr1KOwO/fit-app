package com.example.flexify.data.Storage.DAO

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.flexify.data.dbModel.UserWithConsumedFoods

@Dao
interface UserWithConsumedFoods {
    @Transaction
    @Query("SELECT * FROM User")
    suspend fun getUserWithConsumedFoods():UserWithConsumedFoods
}