// StatisticsViewModel
package com.example.flexify.ui.main_menu.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexify.data.dbModel.Statistics
import com.example.flexify.data.dbModel.User
import com.example.flexify.data.dbModel.UsersWithStatistics
import com.example.flexify.data.domain.usecase.menu.menuUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class StatisticsViewModel @Inject constructor(
    private val menuUseCase: menuUseCase
) : ViewModel() {
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user

    private val _statistics = MutableLiveData<List<Statistics>>()
    val statistics: LiveData<List<Statistics>> get() = _statistics

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _caloriesForWeightLoss = MutableLiveData<Float>()
    val caloriesForWeightLoss: LiveData<Float> get() = _caloriesForWeightLoss

    private val _caloriesForWeightGain = MutableLiveData<Float>()
    val caloriesForWeightGain: LiveData<Float> get() = _caloriesForWeightGain

    private val _caloriesForMaintenance = MutableLiveData<Float>()
    val caloriesForMaintenance: LiveData<Float> get() = _caloriesForMaintenance

    private val _statisticsUpdated = MutableLiveData<Boolean>()
    val statisticsUpdated: LiveData<Boolean> get() = _statisticsUpdated

    private var isTodayStatisticChecked = false
    private lateinit var userWithStatistics: UsersWithStatistics

    init {
        loadUserAndStatistics()
    }

    private fun loadUserAndStatistics() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val usersWithStatistics = menuUseCase.getUsersWithStatistics()
                if (usersWithStatistics.isNotEmpty()) {
                    userWithStatistics = usersWithStatistics.first()
                    _user.value = userWithStatistics.user
                    _statistics.value = userWithStatistics.statistics.toMutableList()
                    calculateCalorieNeeds(userWithStatistics.user)
                }

                try {
                    val statisticsFromServer = menuUseCase.getStatisticsApi(userWithStatistics.user.userId)
                    val combinedStatistics = mergeStatistics(_statistics.value!!, statisticsFromServer)
                    menuUseCase.saveStatistics(combinedStatistics)
                    _statistics.postValue(combinedStatistics)
                } catch (e: Exception) {
                    _error.value = "Ошибка при загрузке данных с сервера: ${e.message}"
                }

                if (!isTodayStatisticChecked) {
                    checkAndCreateTodayStatistic()
                    isTodayStatisticChecked = true
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkAndCreateTodayStatistic() {
        val today = LocalDate.now()
        val currentUser = _user.value ?: return

        val todayStats = _statistics.value?.find { it.date == today }
        if (todayStats == null) {
            val newStat = Statistics(
                idStatistic = 0,
                date = today,
                userId = currentUser.userId,
                caloriesSpent = 0f,
                caloriesConsumed = 0f
            )
            viewModelScope.launch {
                menuUseCase.addStatistic(newStat)
                val updatedStatistics = _statistics.value?.toMutableList() ?: mutableListOf()
                updatedStatistics.add(newStat)
                _statistics.postValue(updatedStatistics)
            }
        }
    }

    private fun mergeStatistics(local: List<Statistics>, server: List<Statistics>): List<Statistics> {
        val merged = mutableListOf<Statistics>()
        val dateSet = mutableSetOf<LocalDate>()

        for (stat in local) {
            merged.add(stat)
            dateSet.add(stat.date)
        }

        for (stat in server) {
            if (!dateSet.contains(stat.date)) {
                merged.add(stat)
                dateSet.add(stat.date)
            }
        }

        return merged
    }

    private fun calculateCalorieNeeds(user: User) {
        val bmr = 10 * user.weight + 6.25f * user.height - 5 * user.age + if (user.sex == "Мужской") 5 else -161

        _caloriesForMaintenance.value = bmr * 1.2f
        _caloriesForWeightLoss.value = _caloriesForMaintenance.value!! - 500
        _caloriesForWeightGain.value = _caloriesForMaintenance.value!! + 500
    }

    fun refreshStatistics() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val usersWithStatistics = menuUseCase.getUsersWithStatistics()
                if (usersWithStatistics.isNotEmpty()) {
                    val userWithStatistics = usersWithStatistics[0]
                    _statistics.value = userWithStatistics.statistics
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addStatistic(statistic: Statistics) {
        viewModelScope.launch {
            try {
                menuUseCase.addStatistic(statistic)
                refreshStatistics()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTodayStatistic(): Statistics? {
        val today = LocalDate.now()
        return _statistics.value?.find { it.date == today }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateCaloriesSpent(caloriesSpent: Float) {
        val todayStatistic = getTodayStatistic()
        if (todayStatistic != null) {
            val updatedStatistic = todayStatistic.copy(
                caloriesSpent = todayStatistic.caloriesSpent + caloriesSpent
            )
            viewModelScope.launch {
                menuUseCase.updateStatistic(updatedStatistic)
                refreshStatistics()
                _statisticsUpdated.postValue(true)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateCaloriesConsumed(caloriesConsumed: Float) {
        val todayStatistic = getTodayStatistic()
        if (todayStatistic != null) {
            val updatedStatistic = todayStatistic.copy(
                caloriesConsumed = caloriesConsumed // Здесь перезаписываем калории, а не добавляем
            )
            viewModelScope.launch {
                menuUseCase.updateStatistic(updatedStatistic)
                refreshStatistics()
                _statisticsUpdated.postValue(true)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun resetCaloriesConsumed() {
        val todayStatistic = getTodayStatistic()
        if (todayStatistic != null) {
            val updatedStatistic = todayStatistic.copy(
                caloriesConsumed = 0f
            )
            viewModelScope.launch {
                menuUseCase.updateStatistic(updatedStatistic)
                refreshStatistics()
                _statisticsUpdated.postValue(true)
            }
        }
    }

    suspend fun savesListStatistics(list: List<Statistics>) {
        menuUseCase.saveStatistics(list)
    }
}

