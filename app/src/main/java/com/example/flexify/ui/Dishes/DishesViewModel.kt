package com.example.flexify.ui.Dishes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexify.data.dbModel.Dish
import com.example.flexify.data.domain.usecase.dishes.DishesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class DishesViewModel @Inject constructor(
    private val dishesUseCase: DishesUseCase
) : ViewModel() {
    private val _dishes = MutableLiveData<List<Dish>>()
    val dishes: LiveData<List<Dish>> get() = _dishes

    private val _currentDish = MutableLiveData<Dish?>()
    val currentDish: LiveData<Dish?> get() = _currentDish

    init {
        loadDishes()
    }

    private fun loadDishes() {
        viewModelScope.launch {
            try {
                val dishList = dishesUseCase.getDishes()
                _dishes.value = dishList
            } catch (e: Exception) {
                e.message
            }
        }
    }

    fun searchExercises(query: String) {
        viewModelScope.launch {
            _dishes.value = dishesUseCase.searchExercises(query)
        }
    }
}