package com.example.flexify.ui.info.Food

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexify.data.dbModel.FoodType
import com.example.flexify.data.domain.usecase.Info.FoodUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class FoodTypeViewModel @Inject constructor(
    private val useCase: FoodUseCase
) : ViewModel() {
    private val _foodTypes = MutableLiveData<List<FoodType>>()
    val foodTypes: LiveData<List<FoodType>> get() = _foodTypes

    init {
        loadFoodTypes()
    }

    private fun loadFoodTypes() {
        viewModelScope.launch {
            _foodTypes.value = useCase.getFoodTypes()
        }
    }

    fun searchFoodTypes(query: String) {
        viewModelScope.launch {
            _foodTypes.value = useCase.getFoodTypes().filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }
}