package com.example.bookolx.editprofile

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

class EditprofileViewModel(tokenArg: String, usernameArg: String, emailArg: String, phoneArg: String, passwordArg: String) : ViewModel() {
    val token = tokenArg
    val username = usernameArg

    val emailEdited = MutableLiveData(emailArg)
    val passwordEdited = MutableLiveData(passwordArg)
    val usernameEdited = MutableLiveData(usernameArg)
    val phoneEdited = MutableLiveData(phoneArg)

    private val _eventEditprofileSuccess = MutableLiveData<Boolean>()
    val eventEditprofileSuccess: LiveData<Boolean>
        get() = _eventEditprofileSuccess

    private val _eventEditprofileFailed = MutableLiveData<Boolean>()
    val eventEditprofileFailed: LiveData<Boolean>
        get() = _eventEditprofileFailed


    fun onEdit() {
        val jsonObject = JSONObject()
        jsonObject.put("email", emailEdited.value)
        jsonObject.put("password", passwordEdited.value)
        jsonObject.put("username", usernameEdited.value)
        jsonObject.put("phone", phoneEdited.value)

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = "Bearer " + token

        val jsonString = jsonObject.toString()
        val requestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = BookApi.retrofitService.editUser(username, headerMap, requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.i("EditprofileViewModel", "A mers")
                    Log.i("EditprofileViewModel", prettyJson)

                    onEditSuccess()
                }
                else {
                    Log.i("EditprofileViewModel", "Nu a mers")
                    Log.i("EditprofileViewModel", "" + response.code() + " " + response.message())
                    onEditFailed()
                }
            }
        }
    }


    fun onEditSuccessComplete() {
        _eventEditprofileSuccess.value = false
    }

    fun onEditSuccess() {
        _eventEditprofileSuccess.value = true
    }

    fun onEditFailedComplete() {
        _eventEditprofileFailed.value = false
    }

    fun onEditFailed() {
        _eventEditprofileFailed.value = true
    }
}