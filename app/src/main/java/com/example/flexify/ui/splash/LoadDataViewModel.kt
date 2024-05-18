package com.example.flexify.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexify.data.domain.loadData.loadDataUseCase
import kotlinx.coroutines.launch

import javax.inject.Inject

class LoadDataViewModel@Inject constructor(
    private val loadDataUseCase: loadDataUseCase ,
): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadData() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                loadDataUseCase.loadFoodType()
                loadDataUseCase.loadFood()
                loadDataUseCase.loadDish()
                loadDataUseCase.loadExercisesType()
                loadDataUseCase.loadExercises()
                loadDataUseCase.loadFitnessLevel()
                loadDataUseCase.loadExerciseToType()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}