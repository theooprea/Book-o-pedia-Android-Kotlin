package com.example.bookolx.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookolx.APIService
import com.example.bookolx.BookApi
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class HomeViewModel(tokenArg: String, emailArg: String, usernameArg: String) : ViewModel() {
    val _token = tokenArg
    private val usernameArgument = usernameArg

    private val _eventDataSuccess = MutableLiveData<Boolean>()
    val eventDataSuccess: LiveData<Boolean>
        get() = _eventDataSuccess

    private val _eventDataFailed = MutableLiveData<Boolean>()
    val eventDataFailed: LiveData<Boolean>
        get() = _eventDataFailed

    private val _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String>
        get() = _phone

    init {
        Log.i("HomeViewModel", "A intrat")
        _username.value = ""
        _email.value = ""
        _phone.value = ""
    }

    fun getData() {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = "Bearer " + _token

        CoroutineScope(Dispatchers.IO).launch {
            val response = BookApi.retrofitService.getUser(usernameArgument, headerMap)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    val jsonObject = JSONObject(prettyJson)
                    _phone.value = jsonObject.getString("phone")
                    _email.value = jsonObject.getString("email")
                    _username.value = jsonObject.getString("username")

                    _eventDataSuccess.value = true

                    Log.i("HomeViewModel", "A mers " + _phone.value)
                }
                else {
                    Log.i("HomeViewModel", "Nu a mers")

                    _eventDataFailed.value = true
                }
            }
        }
    }

    fun getDataComplete() {
        _eventDataSuccess.value = false
    }

    fun getDataFailed() {
        _eventDataFailed.value = false
    }
}