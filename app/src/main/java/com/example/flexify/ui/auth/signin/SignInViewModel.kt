package com.example.flexify.ui.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexify.data.ResponseModel.ResponseSignIn
import com.example.flexify.data.ResponseModel.ResponseStates
import com.example.flexify.data.dbModel.User
import com.example.flexify.data.domain.usecase.signin.signinusecase
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val signInUseCase: signinusecase,
):ViewModel() {
    private val _signInLiveData = MutableLiveData<ResponseStates<ResponseSignIn>>()
    val signInLiveData: LiveData<ResponseStates<ResponseSignIn>> = _signInLiveData

    fun signIn(email:String, password:String){
        viewModelScope.launch {
            _signInLiveData.value = ResponseStates.Loading()
            try {
                _signInLiveData.value = ResponseStates.Success(
                    signInUseCase.signIn(email, password))
            }catch (e:Exception){
                _signInLiveData.value = ResponseStates.Failure(
                    e
                )
            }
        }
    }
}