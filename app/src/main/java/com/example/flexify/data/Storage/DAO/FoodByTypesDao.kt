package com.example.flexify.data.Storage.DAO

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.flexify.data.dbModel.Food
import com.example.flexify.data.dbModel.FoodByTypes

@Dao
interface FoodByTypesDao {
    @Transaction
    @Query("Select * from foodtype")
    suspend fun getFoodByTypes():List<FoodByTypes>
}