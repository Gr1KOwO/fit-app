package com.example.flexify.data.Storage.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.flexify.data.dbModel.ConsumedFoodEntity
import java.time.LocalDate

@Dao
interface ConsumedFoodEntityDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConsumedFoodEntity(consumedFoodEntities:List<ConsumedFoodEntity>)
    @Query("Select * From ConsumedFoodEntity")
    suspend fun getConsumedFoodEntity():List<ConsumedFoodEntity>
    @Update
    suspend fun updateConsumedFoodEntity(consumedFoodEntity:ConsumedFoodEntity)
    @Delete
    suspend fun deleteConsumedFoodEntity(consumedFoodEntity:ConsumedFoodEntity)
    @Query("SELECT * FROM ConsumedFoodEntity WHERE userId = :userId AND date = :date")
    suspend fun getConsumedFoodEntityByDate(userId: Int, date: LocalDate): List<ConsumedFoodEntity>

}