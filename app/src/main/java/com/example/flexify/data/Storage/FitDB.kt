package com.example.flexify.data.Storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.flexify.data.Converter.LocalDateTimeConverter
import com.example.flexify.data.Storage.DAO.*
import com.example.flexify.data.dbModel.*

@Database(entities=[
    User::class,Statistics::class,
    Food::class,FoodType::class,
    Dish::class,ExerciseToType::class,
    ExerciseType::class,Exercise::class,
    ConsumedFoodEntity::class,FitnessLevel::class
   ], version = 1)
@TypeConverters(LocalDateTimeConverter::class)
abstract class fitDB: RoomDatabase() {
    abstract fun usersDao():userDAO
    abstract fun statisticsDao():statisticDAO
    abstract fun userWithStatisticsDAO():UsersWithStatisticsDAO
    abstract fun foodDao():FoodDAO
    abstract fun foodTypeDao():foodTypeDAO
    abstract fun dishDao():dishDAO
    abstract fun exerciseTypeDao():ExerciseTypeDao
    abstract fun exerciseDao():ExerciseDao
    abstract fun consumedFoodEntityDao():ConsumedFoodEntityDAO
    abstract fun consumedFoodWithDishDao():ConsumedFoodWithDishDAO
    abstract fun exerciseToTypeDAO():ExerciseToTypeDAO
    abstract fun typeWithExerciseDao():TypeWithExerciseDao
    abstract fun exerciseWithTypeDao():ExerciseWithTypeDao
    abstract fun consumedFoodWithFoodDao():ConsumedFoodWithFoodDao
    abstract fun foodByTypesDao():FoodByTypesDao
    abstract fun fitnessLevelDao():FitnessLevelDAO
}