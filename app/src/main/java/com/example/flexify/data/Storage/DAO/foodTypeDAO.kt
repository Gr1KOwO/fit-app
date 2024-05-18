package com.example.flexify.data.Storage.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.flexify.data.dbModel.FoodType

@Dao
interface foodTypeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodType(foodTypes:List<FoodType>)

    @Query("Select * from FoodType")
    suspend fun getFoodType():List<FoodType>

    @Update
    suspend fun updateFoodType(foodType: FoodType)

    @Delete
    suspend fun deleteFoodType(foodType: FoodType)
}