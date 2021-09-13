package com.example.bookolx.editbook

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

class EditbookViewModel(tokenArg: String, usernameArg: String, titleArg: String, authorArg: String, genreArg: String, pagesArg: Int, priceArg: Float, quantityArg: Int) : ViewModel() {
    val token = tokenArg
    val username = usernameArg
    val initialTitle = titleArg

    val title = MutableLiveData(titleArg)
    val author = MutableLiveData(authorArg)
    val genre = MutableLiveData(genreArg)
    val pages = MutableLiveData(pagesArg.toString())
    val price = MutableLiveData(priceArg.toString())
    val quantity = MutableLiveData(quantityArg.toString())

    private val _eventEditedSuccess = MutableLiveData<Boolean>()
    val eventEditedSuccess: LiveData<Boolean>
        get() = _eventEditedSuccess

    private val _eventEditedFailed = MutableLiveData<Boolean>()
    val eventEditedFailed: LiveData<Boolean>
        get() = _eventEditedFailed

    fun modifyBook() {
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
            val response = BookApi.retrofitService.editUserBook(username, initialTitle, headerMap, requestBody)

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

                    Log.i("AddbookViewModel", "a mers drq")

                    onBookEditedSuccess()
                }
                else {
                    Log.i("AddbookViewModel", "Nu a mers " + response.code() + " " + response.message() + "\n"
                            + jsonString)

                    onBookEditedFailed()
                }
            }
        }


    }

    fun onBookEditedSuccessComplete() {
        _eventEditedSuccess.value = false
    }

    fun onBookEditedSuccess() {
        _eventEditedSuccess.value = true
    }

    fun onBookEditedFailedComplete() {
        _eventEditedFailed.value = false
    }

    fun onBookEditedFailed() {
        _eventEditedFailed.value = true
    }
}