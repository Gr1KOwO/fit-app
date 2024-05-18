package com.example.flexify.data.domain.usecase.signup

import com.example.flexify.data.ResponseModel.ResponseLogin
import com.example.flexify.data.domain.SignUp
import com.example.flexify.data.repository.FitApiRepository
import javax.inject.Inject

class signupusecase @Inject constructor(
    private val repository: FitApiRepository
) {
    suspend fun createUser(signUp:SignUp):ResponseLogin{
        return repository.createUser(signUp)
    }
}