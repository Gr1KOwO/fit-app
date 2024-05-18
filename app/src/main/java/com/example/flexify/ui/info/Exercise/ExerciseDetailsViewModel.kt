package com.example.flexify.ui.info.Exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexify.data.dbModel.ExerciseWithType
import com.example.flexify.data.domain.usecase.Info.ExercisesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExerciseDetailsViewModel @Inject constructor(
    private val useCase: ExercisesUseCase
) : ViewModel() {
    private val _exerciseWithType = MutableLiveData<ExerciseWithType>()
    val exerciseWithType: LiveData<ExerciseWithType> get() = _exerciseWithType

    fun loadExerciseDetails(exerciseId: Int) {
        viewModelScope.launch {
            _exerciseWithType.value = useCase.getExerciseWithTypes(exerciseId)
        }
    }
}