package com.example.flexify.data.domain.usecase.menu

import com.example.flexify.data.dbModel.ConsumedFoodEntity
import com.example.flexify.data.dbModel.Dish
import com.example.flexify.data.dbModel.Food
import com.example.flexify.data.repository.FitApiRepository
import java.time.LocalDate
import javax.inject.Inject

class ConsumedFoodUseCase@Inject constructor(
    private val repository: FitApiRepository
) {
    suspend fun getDishes(): List<Dish> {
        return repository.getDishes()
    }

    suspend fun getFoods(): List<Food> {
        return repository.getFood()
    }

    suspend fun insertConsumedFood(consumedFoodEntities: List<ConsumedFoodEntity>) {
        repository.insertConsumedFoodEntity(consumedFoodEntities)
    }

    suspend fun getConsumedFoodEntityByDate(userId: Int, date: LocalDate): List<ConsumedFoodEntity> {
        return repository.getConsumedFoodEntityByDate(userId, date)
    }

    suspend fun updateConsumedFoodEntity(consumedFoodEntity: ConsumedFoodEntity) {
        repository.updateConsumedFoodEntity(consumedFoodEntity)
    }

    suspend fun deleteConsumedFoodEntity(consumedFoodEntity: ConsumedFoodEntity) {
        repository.deleteConsumedFoodEntity(consumedFoodEntity)
    }
}