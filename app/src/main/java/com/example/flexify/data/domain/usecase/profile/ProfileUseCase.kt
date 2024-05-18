package com.example.flexify.data.domain.usecase.profile

import com.example.flexify.data.dbModel.User
import com.example.flexify.data.repository.FitApiRepository
import com.example.flexify.data.requestmodel.UserUpdateRequest
import javax.inject.Inject

class profileUseCase @Inject constructor(
    private val repository: FitApiRepository,
){
    suspend fun getUser():User?{
        return repository.getUser()
    }

    suspend fun updateUser(user: User, newPassword: String?){
        repository.updateLocaleUser(user)
        repository.updateUser(
            UserUpdateRequest(
            fullName = user.fullName,
            age = user.age,
            weight = user.weight,
            height = user.height,
            sex = user.sex,
            password = newPassword)
        )
    }

    suspend fun execute(user: User) {
        repository.deleteUser(user)
    }
}