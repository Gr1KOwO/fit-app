package com.example.flexify.data.Storage.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.flexify.data.dbModel.Food


@Dao
interface FoodDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFood(listFood:List<Food>)

    @Query("Select * from Food")
    suspend fun getFoods():List<Food>

    @Update
    suspend fun updateFood(food:Food)

    @Delete
    suspend fun deleteFood(food:Food)
}