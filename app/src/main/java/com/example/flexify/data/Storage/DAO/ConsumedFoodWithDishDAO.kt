package com.example.flexify.data.Storage.DAO

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.flexify.data.dbModel.Dish

@Dao
interface ConsumedFoodWithDishDAO {
    @Transaction
    @Query("SELECT * FROM Dish")
    fun getFoodWithConsumedFood():Dish?
}