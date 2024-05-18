package com.example.flexify.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flexify.data.dbModel.User
import com.example.flexify.data.domain.usecase.profile.profileUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val profileUseCase: profileUseCase
):ViewModel() {
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getUser() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val userData = profileUseCase.getUser()
                _user.value = userData
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateUser(user: User, newPassword: String?) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                profileUseCase.updateUser(user,newPassword)
                _user.value = user
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteUser() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val currentUser = _user.value
                if (currentUser != null) {
                    profileUseCase.execute(currentUser)
                    _user.value = null
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun signOut() {
        deleteUser()
    }
}