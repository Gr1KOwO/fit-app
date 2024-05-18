package com.example.flexify.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flexify.data.ResponseModel.ResponseStates
import com.example.flexify.data.domain.SignUp
import com.example.flexify.data.domain.usecase.signup.signupusecase
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.example.flexify.data.ResponseModel.ResponseLogin
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val signupusecase: signupusecase
):ViewModel(){
    private val _signupLiveData = MutableLiveData<ResponseStates<ResponseLogin>>()
    val signupLiveData: LiveData<ResponseStates<ResponseLogin>> = _signupLiveData

    fun createUser(signUp: SignUp) {
        viewModelScope.launch {
            _signupLiveData.value = ResponseStates.Loading()
            try {
                _signupLiveData.value = ResponseStates.Success(
                    signupusecase.createUser(signUp)
                )
            } catch (e: Exception) {
                _signupLiveData.value = ResponseStates.Failure(
                    e
                )
            }
        }
    }
}