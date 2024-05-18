package com.example.flexify.data.domain.usecase.Info

import com.example.flexify.data.dbModel.Exercise
import com.example.flexify.data.dbModel.ExerciseWithType
import com.example.flexify.data.repository.FitApiRepository
import javax.inject.Inject

class ExercisesUseCase @Inject constructor(
    private val repository: FitApiRepository
){
    suspend fun getExercises(): List<Exercise> {
        return repository.getExercises()
    }

    suspend fun getExerciseWithTypes(exerciseId: Int): ExerciseWithType? {
        val exercisesWithTypes = repository.getExercisesWithTypes()
        return exercisesWithTypes.find { it.exercise.id == exerciseId }
    }

    suspend fun searchExercises(query: String): List<Exercise> {
        return repository.getExercises().filter {
            it.name.contains(query, ignoreCase = true)
        }
    }
}