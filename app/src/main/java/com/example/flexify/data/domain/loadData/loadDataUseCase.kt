package com.example.flexify.data.domain.loadData

import com.example.flexify.data.dbModel.Dish
import com.example.flexify.data.dbModel.Exercise
import com.example.flexify.data.dbModel.ExerciseToType
import com.example.flexify.data.dbModel.ExerciseType
import com.example.flexify.data.dbModel.FitnessLevel
import com.example.flexify.data.dbModel.Food
import com.example.flexify.data.dbModel.FoodType
import com.example.flexify.data.dbModel.Statistics
import com.example.flexify.data.repository.FitApiRepository
import javax.inject.Inject

class loadDataUseCase @Inject constructor(
    private val repository: FitApiRepository) {
    suspend fun loadFoodType():List<FoodType>{
        return repository.getFoodType().ifEmpty{
            val list = repository.getFoodTypeApi()
            repository.insertFoodTypeList(list)
            return@ifEmpty list
        }
    }

    suspend fun loadFood():List<Food>{
        return repository.getFood().ifEmpty {
            val list = repository.getFoodApi()
            repository.saveFood(list)
            return@ifEmpty list
        }
    }

    suspend fun loadDish():List<Dish>{
        return repository.getDishes().ifEmpty {
            val list = repository.getDishesApi()
            repository.saveDishes(list)
            return@ifEmpty list
        }
    }

    suspend fun loadExercisesType():List<ExerciseType>{
        return repository.getExercisesTypes().ifEmpty {
            val list = repository.getExercisesTypesApi()
            repository.saveExercisesTypes(list)
            return@ifEmpty list
        }
    }

    suspend fun loadExercises():List<Exercise>{
        return repository.getExercises().ifEmpty {
            val list = repository.getExercisesApi()
            repository.saveExercises(list)
            return@ifEmpty list
        }
    }

    suspend fun loadFitnessLevel():List<FitnessLevel>{
        return repository.getFitnessLevel().ifEmpty{
            val list = repository.getFitnessLevelApi()
            repository.saveFitnessLevel(list)
            return@ifEmpty list
        }
    }

    suspend fun loadExerciseToType():List<ExerciseToType>{
        return repository.getExercisetotype().ifEmpty{
            val list = repository.getExercisetotypeApi()
            repository.saveExercisesToType(list)
            return@ifEmpty list
        }
    }
}