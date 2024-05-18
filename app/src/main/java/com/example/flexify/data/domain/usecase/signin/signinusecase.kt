package com.example.flexify.data.domain.usecase.signin

import com.example.flexify.data.ResponseModel.ResponseSignIn
import com.example.flexify.data.dbModel.User
import com.example.flexify.data.repository.FitApiRepository
import com.example.flexify.data.repository.PreferenceStorage
import javax.inject.Inject

class signinusecase@Inject constructor(
    private val repository: FitApiRepository,
    private val preferenceStorage: PreferenceStorage,
) {
    suspend fun signIn(email: String,password:String):ResponseSignIn{
        val signInData = repository.signInUser(email,password)
        preferenceStorage.userToken = signInData.token
        val user = User(
            userId = signInData.user.userId,
            fullName = signInData.user.fullName,
            age =signInData.user.age,
            weight = signInData.user.weight,
            height = signInData.user.height,
            sex=signInData.user.sex
        )
        repository.addUserInDB(user)
        return signInData
    }
}