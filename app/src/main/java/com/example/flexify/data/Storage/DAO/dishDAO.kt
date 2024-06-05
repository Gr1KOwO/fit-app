package com.example.flexify.data.Storage.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.flexify.data.dbModel.Dish

@Dao
interface dishDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDishes(listDish:List<Dish>)

    @Query("Select * From Dish")
    suspend fun getDishes():List<Dish>

    @Update
    suspend fun updateDish(dish: Dish)

    @Delete
    suspend fun delDish(dish: Dish)

    @Query("Select * From Dish where id = :id")
    suspend fun getDishById(id:Int):Dish
}