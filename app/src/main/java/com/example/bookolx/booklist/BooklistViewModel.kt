package com.example.bookolx.booklist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookolx.Book
import com.example.bookolx.BookApi
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class BooklistViewModel(tokenArg: String, usernameArg: String) : ViewModel() {
    var titles : Array<String> = arrayOf("a", "b", "c", "d", "e")
    var booksArrayList : ArrayList<Book> = arrayListOf()
    var token = tokenArg
    var username = usernameArg

    private val _eventDataSuccess = MutableLiveData<Boolean>()
    val eventDataSuccess: LiveData<Boolean>
        get() = _eventDataSuccess

    fun getData() {
        booksArrayList.clear()

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = "Bearer " + token

        CoroutineScope(Dispatchers.IO).launch {
            val response = BookApi.retrofitService.getUserBooks(username, headerMap)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    val jsonArray = JSONArray(prettyJson)

                    for (i in 0 until jsonArray.length()) {
                        val jsonBook = jsonArray.getJSONObject(i)
                        val jsonBookTitle = jsonBook.getString("title")
                        val jsonBookAuthor = jsonBook.getString("author")
                        val jsonBookGenre = jsonBook.getString("genre")
                        val jsonBookPrice = jsonBook.getDouble("price")
                        val jsonBookPages = jsonBook.getInt("pages")
                        val jsonBookQuantity = jsonBook.getInt("quantity")
                        val jsonBookSeller = jsonBook.getString("seller")

                        val book = Book(jsonBookTitle, jsonBookAuthor, jsonBookGenre, jsonBookPages, jsonBookPrice, jsonBookQuantity, jsonBookSeller)
                        booksArrayList.add(book)
                    }

                    _eventDataSuccess.value = true

                    Log.i("BooklistViewModel", "A mers " + jsonArray + "\n" + prettyJson)
                    Log.i("BooklistViewModel", "A mers " + response.body()!!.string())
                }
                else {
                    Log.i("BooklistViewModel", "Nu a mers")
                }
            }
        }
    }

    fun getDataComplete() {
        _eventDataSuccess.value = false
    }
}