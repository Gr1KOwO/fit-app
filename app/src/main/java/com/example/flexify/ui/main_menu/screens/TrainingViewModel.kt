package com.example.flexify.ui.main_menu.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexify.data.dbModel.Exercise
import com.example.flexify.data.dbModel.ExerciseInWorkoutEntity
import com.example.flexify.data.dbModel.ExerciseType
import com.example.flexify.data.dbModel.ExerciseWithType
import com.example.flexify.data.dbModel.FitnessLevel
import com.example.flexify.data.domain.usecase.training.TrainingUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class TrainingViewModel @Inject constructor(
    private val trainingUseCase: TrainingUseCase
) : ViewModel()
{
    private val _fitnessLevels = MutableLiveData<List<FitnessLevel>>()
    val fitnessLevels: LiveData<List<FitnessLevel>> get() = _fitnessLevels

    private val _exerciseTypes = MutableLiveData<List<ExerciseType>>()
    val exerciseTypes: LiveData<List<ExerciseType>> get() = _exerciseTypes

    private val _generatedWorkout = MutableLiveData<List<ExerciseInWorkoutEntity>>()
    val generatedWorkout: LiveData<List<ExerciseInWorkoutEntity>> get() = _generatedWorkout

    fun loadFitnessLevels() {
        viewModelScope.launch {
            _fitnessLevels.value = trainingUseCase.getFitnessLevels()
        }
    }

    fun loadExerciseTypes() {
        viewModelScope.launch {
            _exerciseTypes.value = trainingUseCase.getExerciseTypes()
        }
    }

    fun generateWorkout(level: FitnessLevel, selectedTypes: List<ExerciseType>) {
        viewModelScope.launch {
            val allExercises = trainingUseCase.getExercises()
            val filteredExercises = if (selectedTypes.isEmpty()) {
                allExercises
            } else {
                allExercises.filter { exercise ->
                    exercise.exercisesType.any { type -> selectedTypes.contains(type) }
                }
            }
            val exercises = generateRandomExercises(level, filteredExercises)
            _generatedWorkout.value = exercises
        }
    }

    private fun generateRandomExercises(level: FitnessLevel, exercises: List<ExerciseWithType>): List<ExerciseInWorkoutEntity> {
        val exerciseList = mutableListOf<ExerciseInWorkoutEntity>()
        val numberOfExercises = when (level.name) {
            "Начальный уровень" -> (1..2).random()
            "Средний уровень" -> (2..3).random()
            "Продвинутый уровень" -> (3..5).random()
            else -> 1
        }
        val sets = when (level.name) {
            "Начальный уровень" -> (1..2).random()
            "Средний уровень" -> (2..3).random()
            "Продвинутый уровень" -> (3..5).random()
            else -> 1
        }
        val reps = when (level.name) {
            "Начальный уровень" -> (8..12).random()
            "Средний уровень" -> (12..15).random()
            "Продвинутый уровень" -> (15..20).random()
            else -> 8
        }

        exercises.shuffled().take(numberOfExercises).forEachIndexed { index, exercise ->
            exerciseList.add(
                ExerciseInWorkoutEntity(
                    id = index,
                    exercise = exercise,
                    sets = sets,
                    reps = reps,
                    weight = null,
                    met = (6..8).random().toFloat(),
                    isCompleted = false,
                    isSkipped = false
                )
            )
        }

        return exerciseList
    }
}