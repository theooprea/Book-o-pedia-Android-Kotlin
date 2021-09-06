package com.example.bookolx.register

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

class RegisterViewModel : ViewModel() {
    val emailRegister = MutableLiveData<String>()
    val passwordRegister = MutableLiveData<String>()
    val usernameRegister = MutableLiveData<String>()
    val phoneRegister = MutableLiveData<String>()

    private val _eventRegisterSuccess = MutableLiveData<Boolean>()
    val eventRegisterSuccess: LiveData<Boolean>
        get() = _eventRegisterSuccess

    init {
        Log.i("RegisterViewModel", "init")
    }

    override fun onCleared() {
        Log.i("RegisterViewModel", "destroyed")
        super.onCleared()
    }

    fun onRegister() {
        val jsonObject = JSONObject()
        jsonObject.put("email", emailRegister.value)
        jsonObject.put("password", passwordRegister.value)
        jsonObject.put("username", usernameRegister.value)
        jsonObject.put("phone", phoneRegister.value)

        val jsonString = jsonObject.toString()
        val requestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = BookApi.retrofitService.register(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.i("RegisterViewModel", "A mers")
                    Log.i("RegisterViewModel", prettyJson)

                    onRegisterSuccess()
                }
                else {
                    Log.i("RegisterViewModel", "Nu a mers")
                    Log.i("RegisterViewModel", "" + response.code() + " " + response.message())
                }
            }
        }
    }


    fun onRegisterSuccessComplete() {
        _eventRegisterSuccess.value = false
    }

    fun onRegisterSuccess() {
        _eventRegisterSuccess.value = true
    }
}