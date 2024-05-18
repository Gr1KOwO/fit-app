package com.example.flexify.data.domain.usecase.dishes

import com.example.flexify.data.dbModel.Dish
import com.example.flexify.data.repository.FitApiRepository
import javax.inject.Inject

class DishesUseCase @Inject constructor(
    private val repository: FitApiRepository
) {
    suspend fun getDishes(): List<Dish> {
        return repository.getDishes()
    }
}