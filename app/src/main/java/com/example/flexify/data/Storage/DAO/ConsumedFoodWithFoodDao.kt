package com.example.flexify.data.Storage.DAO

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.flexify.data.dbModel.Food

@Dao
interface ConsumedFoodWithFoodDao {
    @Transaction
    @Query("Select * from Food")
    suspend fun getConsumedFoodWithFoodDao(): Food?
}