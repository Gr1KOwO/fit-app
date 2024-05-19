// ConsumedViewModel
package com.example.flexify.ui.main_menu.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexify.data.dbModel.ConsumedFoodEntity
import com.example.flexify.data.dbModel.Dish
import com.example.flexify.data.dbModel.Food
import com.example.flexify.data.domain.usecase.menu.ConsumedFoodUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
class ConsumedViewModel @Inject constructor(
    private val consumedFoodUseCase: ConsumedFoodUseCase,
    private val statisticsViewModel: StatisticsViewModel
) : ViewModel() {
    private val _dishes = MutableLiveData<List<Dish>>()
    val dishes: LiveData<List<Dish>> get() = _dishes

    private val _foods = MutableLiveData<List<Food>>()
    val foods: LiveData<List<Food>> get() = _foods

    private val _consumedFood = MutableLiveData<List<ConsumedFoodEntity>>()
    val consumedFood: LiveData<List<ConsumedFoodEntity>> get() = _consumedFood

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private var userId: Int = 0

    init {
        statisticsViewModel.user.observeForever { user ->
            user?.let {
                userId = it.userId
                loadConsumedFoodForToday()
            }
        }
        loadDishesAndFoods()

        statisticsViewModel.statisticsUpdated.observeForever {
            loadConsumedFoodForToday()
        }
    }

    private fun loadDishesAndFoods() {
        viewModelScope.launch {
            try {
                _foods.value = consumedFoodUseCase.getFoods()
                _dishes.value = consumedFoodUseCase.getDishes()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    private fun loadConsumedFoodForToday() {
        val today = LocalDate.now()
        viewModelScope.launch {
            try {
                _consumedFood.value = consumedFoodUseCase.getConsumedFoodEntityByDate(userId, today)
                updateStatistics() // Пересчитать статистику сразу после загрузки данных
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun addConsumedFood(foodId: Int?, dishId: Int?, quantity: Int, calories: Float) {
        val today = LocalDate.now()
        val consumedFoodEntity = ConsumedFoodEntity(
            id = 0,
            date = today,
            userId = userId,
            foodId = foodId,
            dishId = dishId,
            quantity = quantity,
            calories = calories
        )

        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    consumedFoodUseCase.insertConsumedFood(listOf(consumedFoodEntity))
                }
                loadConsumedFoodForToday()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun updateConsumedFood(consumedFoodEntity: ConsumedFoodEntity) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    consumedFoodUseCase.updateConsumedFoodEntity(consumedFoodEntity)
                }
                loadConsumedFoodForToday()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun deleteConsumedFood(consumedFoodEntity: ConsumedFoodEntity) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    consumedFoodUseCase.deleteConsumedFoodEntity(consumedFoodEntity)
                }
                loadConsumedFoodForToday()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    private fun updateStatistics() {
        val totalCaloriesConsumed = _consumedFood.value?.sumOf { it.calories.toDouble() }?.toFloat() ?: 0f
        statisticsViewModel.updateCaloriesConsumed(totalCaloriesConsumed)
    }

    fun getFoodNameById(id: Int): String? {
        return _foods.value?.find { it.id == id }?.name
    }

    fun getDishNameById(id: Int): String? {
        return _dishes.value?.find { it.id == id }?.name
    }
}

