package com.example.flexify.data.domain.usecase.Info

import com.example.flexify.data.dbModel.Food
import com.example.flexify.data.dbModel.FoodType
import com.example.flexify.data.repository.FitApiRepository
import javax.inject.Inject

class FoodUseCase @Inject constructor(private val repository: FitApiRepository) {

    suspend fun getFoodTypes(): List<FoodType> {
        return repository.getFoodType()
    }

    suspend fun getFoodsByType(foodTypeId: Int): List<Food> {
        return repository.getFood().filter {
            it.foodTypeId == foodTypeId
        }
    }

    suspend fun searchFoodsByType(foodTypeId: Int, query: String): List<Food> {
        return repository.getFood().filter {
            it.foodTypeId == foodTypeId && it.name.contains(query, ignoreCase = true)
        }
    }
}