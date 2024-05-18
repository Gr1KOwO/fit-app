package com.example.flexify.ui.info.Food

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexify.data.dbModel.Food
import com.example.flexify.data.domain.usecase.Info.FoodUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class FoodListViewModel @Inject constructor(
    private val useCase: FoodUseCase
) : ViewModel() {
    private val _foods = MutableLiveData<List<Food>>()
    val foods: LiveData<List<Food>> get() = _foods

    fun loadFoods(foodTypeId: Int) {
        viewModelScope.launch {
            _foods.value = useCase.getFoodsByType(foodTypeId)
        }
    }

    fun searchFoods(foodTypeId: Int, query: String) {
        viewModelScope.launch {
            _foods.value = useCase.searchFoodsByType(foodTypeId, query)
        }
    }
}