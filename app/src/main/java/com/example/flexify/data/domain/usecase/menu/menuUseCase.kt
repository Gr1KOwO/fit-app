package com.example.flexify.data.domain.usecase.menu

import com.example.flexify.data.dbModel.Statistics
import com.example.flexify.data.dbModel.UsersWithStatistics
import com.example.flexify.data.repository.FitApiRepository
import javax.inject.Inject

class menuUseCase@Inject constructor(
    private val repository: FitApiRepository,
) {

    suspend fun saveStatistics(list: List<Statistics>){
        repository.saveStatistics(list)
    }

    suspend fun getUsersWithStatistics(): List<UsersWithStatistics> {
        return repository.getUserWithStatistics()
    }
    suspend fun addStatistic(statistic: Statistics) {
        repository.addStatistic(statistic)
    }
    suspend fun updateStatistic(statistic: Statistics){
        repository.updateStatistic(statistic)
    }

    suspend fun getStatisticsApi(userId: Int): List<Statistics> {
        return repository.getStatisticsApi(userId)
    }
}