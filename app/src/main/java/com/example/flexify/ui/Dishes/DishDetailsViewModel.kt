package com.example.flexify.ui.Dishes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexify.data.dbModel.Dish
import com.example.flexify.data.dbModel.ExerciseWithType
import com.example.flexify.data.domain.usecase.dishes.DishesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class DishDetailsViewModel @Inject constructor(
    private val dishesUseCase: DishesUseCase
) : ViewModel()  {

    private val _dish = MutableLiveData<Dish>()
    val dish: LiveData<Dish> get() = _dish

    fun loadDishDetails(dishId: Int) {
        viewModelScope.launch {
            _dish.value = dishesUseCase.getDishById(dishId)
        }
    }
}