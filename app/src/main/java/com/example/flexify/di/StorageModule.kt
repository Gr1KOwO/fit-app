package com.example.flexify.di

import android.content.Context
import androidx.room.Room
import com.example.flexify.data.Storage.DAO.*
import com.example.flexify.data.Storage.fitDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {
    @Singleton
    @Provides
    fun provideFitDB(context:Context):fitDB{
        return Room.databaseBuilder(context,fitDB::class.java,"fitDB")
            .fallbackToDestructiveMigration().build()
    }
    @Provides
    fun provideUserDAO(database: fitDB): userDAO{
        return database.usersDao()
    }
    @Provides
    fun provideStatisticDAO(database: fitDB):statisticDAO{
        return database.statisticsDao()
    }
    @Provides
    fun provideUserWithStatisticsDAO(database: fitDB):UsersWithStatisticsDAO{
        return database.userWithStatisticsDAO()
    }
    @Provides
    fun provideFoodDAO(database: fitDB):FoodDAO{
        return database.foodDao()
    }
    @Provides
    fun provideFoodTypeDAO(database: fitDB): foodTypeDAO {
        return database.foodTypeDao()
    }
    @Provides
    fun provideDishDAO(database: fitDB):dishDAO{
        return database.dishDao()
    }
    @Provides
    fun provideExerciseTypeDao(database: fitDB): ExerciseTypeDao {
        return database.exerciseTypeDao()
    }
    @Provides
    fun provideExerciseDao(database: fitDB): ExerciseDao {
        return database.exerciseDao()
    }
    @Provides
    fun provideConsumedFoodEntityDAO(database: fitDB): ConsumedFoodEntityDAO {
        return database.consumedFoodEntityDao()
    }
    @Provides
    fun provideConsumedFoodWithDishDAO(database: fitDB):ConsumedFoodWithDishDAO{
        return database.consumedFoodWithDishDao()
    }
    @Provides
    fun provideExerciseToTypeDAO(database: fitDB):ExerciseToTypeDAO{
        return database.exerciseToTypeDAO()
    }
    @Provides
    fun provideExerciseWithTypeDao(database: fitDB):ExerciseWithTypeDao{
        return database.exerciseWithTypeDao()
    }
    @Provides
    fun provideTypeWithExerciseDao(database: fitDB):TypeWithExerciseDao{
        return database.typeWithExerciseDao()
    }
    @Provides
    fun provideConsumedFoodWithFoodDao(database: fitDB):ConsumedFoodWithFoodDao{
        return database.consumedFoodWithFoodDao()
    }
    @Provides
    fun provideFoodByTypesDao(database: fitDB):FoodByTypesDao{
        return database.foodByTypesDao()
    }
    @Provides
    fun provideFitnessLevelDao(database: fitDB):FitnessLevelDAO{
        return database.fitnessLevelDao()
    }
}