package com.example.bookolx.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookolx.BookApi
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class LoginViewModel : ViewModel() {
    val emailLogin = MutableLiveData<String>()
    val passwordLogin = MutableLiveData<String>()

    private val _token = MutableLiveData<String>()
    val token: LiveData<String>
        get() = _token

    private val _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username

    private val _eventLoginSuccess = MutableLiveData<Boolean>()
    val eventLoginSuccess: LiveData<Boolean>
        get() = _eventLoginSuccess

    private val _eventLoginFailed = MutableLiveData<Boolean>()
    val eventLoginFailed: LiveData<Boolean>
        get() = _eventLoginFailed

    init {
        Log.i("LoginViewModel", "init")
    }

    override fun onCleared() {
        Log.i("LoginViewModel", "destroyed")
        super.onCleared()
    }

    fun onLogin() {
        val jsonObject = JSONObject()
        jsonObject.put("email", emailLogin.value)
        jsonObject.put("password", passwordLogin.value)

        val jsonString = jsonObject.toString()
        val requestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = BookApi.retrofitService.login(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.i("LoginViewModel", "A mers")
                    Log.i("LoginViewModel", prettyJson)

                    val jsonObject : JSONObject = JSONObject(prettyJson)
                    val token = jsonObject.getString("token")
                    val username = jsonObject.getString("username")

                    _token.value = token
                    _username.value = username

                    Log.i("LoginViewModel", token)

                    onLoginSuccess()
                }
                else {
                    Log.i("LoginViewModel", "Nu a mers")
                    onLoginFailed()
                }
            }
        }
    }

    fun onLoginSuccessComplete() {
        _eventLoginSuccess.value = false
    }

    fun onLoginSuccess() {
        _eventLoginSuccess.value = true
    }

    fun onLoginFailedComplete() {
        _eventLoginFailed.value = false
    }

    fun onLoginFailed() {
        _eventLoginFailed.value = true
    }
}