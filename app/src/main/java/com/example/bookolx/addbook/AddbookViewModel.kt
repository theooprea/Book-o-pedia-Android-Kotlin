package com.example.bookolx.addbook

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

class AddbookViewModel(tokenArg: String, usernameArg: String) : ViewModel() {
    val token = tokenArg
    val username = usernameArg

    val title = MutableLiveData<String>()
    val author = MutableLiveData<String>()
    val genre = MutableLiveData<String>()
    val pages = MutableLiveData<String>()
    val price = MutableLiveData<String>()
    val quantity = MutableLiveData<String>()

    private val _eventCreatedSuccess = MutableLiveData<Boolean>()
    val eventCreatedSuccess: LiveData<Boolean>
        get() = _eventCreatedSuccess

    fun onAddBook() {
        if (title.value == null || author.value == null || genre.value == null ||
            pages.value == null|| price.value == null || quantity.value == null) {
            Log.i("AddbookViewModel", "ceva e null")
            return
        }

        val jsonObject = JSONObject()
        jsonObject.put("title", title.value)
        jsonObject.put("author", author.value)
        jsonObject.put("genre", genre.value)
        jsonObject.put("pages", pages.value!!.toInt())
        jsonObject.put("price", price.value!!.toDouble())
        jsonObject.put("quantity", quantity.value!!.toInt())
        jsonObject.put("seller", username)

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = "Bearer " + token

        val jsonString = jsonObject.toString()
        val requestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = BookApi.retrofitService.addUserBook(username, headerMap, requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.i("AddbookViewModel", "A mers")
                    Log.i("AddbookViewModel", prettyJson)

                    onBookCreatedSuccess()
                }
                else {
                    Log.i("AddbookViewModel", "Nu a mers " + response.code() + " " + response.message() + "\n"
                    + jsonString)
                }
            }
        }
    }

    fun onBookCreatedSuccessComplete() {
        _eventCreatedSuccess.value = false
    }

    fun onBookCreatedSuccess() {
        _eventCreatedSuccess.value = true
    }
}