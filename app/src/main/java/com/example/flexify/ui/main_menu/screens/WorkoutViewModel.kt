// WorkoutViewModel
package com.example.flexify.ui.main_menu.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexify.data.dbModel.ExerciseInWorkoutEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

class WorkoutViewModel @Inject constructor(
    private val statisticsViewModel: StatisticsViewModel
) : ViewModel() {
    private val _workout = MutableLiveData<List<ExerciseInWorkoutEntity>>()
    val workout: LiveData<List<ExerciseInWorkoutEntity>> get() = _workout

    private val _currentExerciseIndex = MutableLiveData<Int>()
    val currentExerciseIndex: LiveData<Int> get() = _currentExerciseIndex

    private val _totalElapsedTime = MutableLiveData<Long>()
    val totalElapsedTime: LiveData<Long> get() = _totalElapsedTime

    private val _currentRestTime = MutableLiveData<Long>()
    val currentRestTime: LiveData<Long> get() = _currentRestTime

    private val _workoutFinished = MutableLiveData<Boolean>()
    val workoutFinished: LiveData<Boolean> get() = _workoutFinished

    init {
        _currentExerciseIndex.value = 0
        _totalElapsedTime.value = 0
        _currentRestTime.value = 10000
        _workoutFinished.value = false
    }

    fun setWorkout(workout: List<ExerciseInWorkoutEntity>) {
        _workout.value = workout
    }

    fun incrementExerciseIndex() {
        _currentExerciseIndex.value = _currentExerciseIndex.value?.plus(1)
    }

    fun addElapsedTime(time: Long) {
        _totalElapsedTime.value = _totalElapsedTime.value?.plus(time)
    }

    fun setRestTime(time: Long) {
        _currentRestTime.value = time
    }

    fun addRestTime(time: Long) {
        _currentRestTime.value = _currentRestTime.value?.plus(time)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun markWorkoutAsFinished() {
        _workoutFinished.value = true
        calculateAndSaveCalories()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateAndSaveCalories() {
        val totalCaloriesBurned = _workout.value?.filter { it.isCompleted }
            ?.sumOf { (it.met * 3.5 * 70 / 200 * ((_totalElapsedTime.value ?: 0) / 60)) / 1000.0 } // Примерная формула расчета потраченных калорий

        viewModelScope.launch {
            statisticsViewModel.updateCaloriesSpent(totalCaloriesBurned?.toFloat() ?: 0f)
            _workoutFinished.value = false
        }
    }
}

