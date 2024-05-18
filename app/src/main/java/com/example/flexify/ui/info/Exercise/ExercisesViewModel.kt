package com.example.flexify.ui.info.Exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexify.data.dbModel.Exercise
import com.example.flexify.data.domain.usecase.Info.ExercisesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExercisesViewModel @Inject constructor(
    private val useCase: ExercisesUseCase
) : ViewModel() {

    private val _exercises = MutableLiveData<List<Exercise>>()
    val exercises: LiveData<List<Exercise>> get() = _exercises

    init {
        loadExercises()
    }

    private fun loadExercises() {
        viewModelScope.launch {
            _exercises.value = useCase.getExercises()
        }
    }

    fun searchExercises(query: String) {
        viewModelScope.launch {
            _exercises.value = useCase.searchExercises(query)
        }
    }
}