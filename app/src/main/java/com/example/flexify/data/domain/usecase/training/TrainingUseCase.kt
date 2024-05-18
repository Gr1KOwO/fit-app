package com.example.flexify.data.domain.usecase.training

import com.example.flexify.data.dbModel.Exercise
import com.example.flexify.data.dbModel.ExerciseType
import com.example.flexify.data.dbModel.ExerciseWithType
import com.example.flexify.data.dbModel.FitnessLevel
import com.example.flexify.data.repository.FitApiRepository
import javax.inject.Inject

class TrainingUseCase @Inject constructor(
    private val repository: FitApiRepository
) {
    suspend fun getFitnessLevels(): List<FitnessLevel> {
        return repository.getFitnessLevel()
    }

    suspend fun getExerciseTypes(): List<ExerciseType> {
        return repository.getExercisesTypes()
    }

    suspend fun getExercises():List<ExerciseWithType>{
        return repository.getExercisesWithTypes()
    }
}