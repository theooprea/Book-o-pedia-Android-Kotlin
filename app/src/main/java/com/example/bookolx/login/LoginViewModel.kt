package com.example.bookolx.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    val emailLogin = MutableLiveData<String>()
    val passwordLogin = MutableLiveData<String>()

    private val _eventLoginSuccess = MutableLiveData<Boolean>()
    val eventLoginSuccess: LiveData<Boolean>
        get() = _eventLoginSuccess

    init {
        Log.i("LoginViewModel", "init")
    }

    override fun onCleared() {
        Log.i("LoginViewModel", "destroyed")
        super.onCleared()
    }

    fun onLogin() {
        onLoginSuccess()
    }

    fun onRegister() {
        Log.i("LoginViewModel", "register")
    }

    fun onLoginSuccessComplete() {
        _eventLoginSuccess.value = false
    }

    fun onLoginSuccess() {
        _eventLoginSuccess.value = true
    }
}