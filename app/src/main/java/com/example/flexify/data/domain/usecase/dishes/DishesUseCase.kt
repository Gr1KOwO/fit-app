package com.example.flexify.data.domain.usecase.dishes

import com.example.flexify.data.dbModel.Dish
import com.example.flexify.data.dbModel.Exercise
import com.example.flexify.data.repository.FitApiRepository
import javax.inject.Inject

class DishesUseCase @Inject constructor(
    private val repository: FitApiRepository
) {
    suspend fun getDishes(): List<Dish> {
        return repository.getDishes()
    }

    suspend fun getDishById(id:Int):Dish{
        return repository.getDishById(id)
    }

    suspend fun searchExercises(query: String): List<Dish> {
        return repository.getDishes().filter {
            it.name.contains(query, ignoreCase = true)
        }
    }
}