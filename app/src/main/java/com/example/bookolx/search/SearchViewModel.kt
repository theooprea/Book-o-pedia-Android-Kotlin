package com.example.bookolx.search

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

class SearchViewModel(tokenArg: String, usernameArg: String) : ViewModel() {
    var booksArrayList : ArrayList<Book> = arrayListOf()
    var token = tokenArg
    var username = usernameArg

    val titleInput = MutableLiveData<String>()
    val genreInput = MutableLiveData<String>()

    private val _eventDataSuccess = MutableLiveData<Boolean>()
    val eventDataSuccess: LiveData<Boolean>
        get() = _eventDataSuccess

    fun getData(title: String?, genre: String?, comparator: Comparator<Book>) {
        booksArrayList.clear()

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = "Bearer " + token

        CoroutineScope(Dispatchers.IO).launch {
            val response = BookApi.retrofitService.getBooks(headerMap)

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

                        val book = Book(jsonBookTitle, jsonBookAuthor, jsonBookGenre, jsonBookPages, jsonBookPrice, jsonBookQuantity)
                        booksArrayList.add(book)
                    }

                    if (title != null || genre != null) {
                        Log.i("SearchViewModel", "ceva nu i bun " + title + " " + genre)
                    }

                    booksArrayList.sortWith(comparator)
                    getDataSuccess()

                    Log.i("SearchViewModel", "A mers " + jsonArray + "\n" + prettyJson)
                    Log.i("SearchViewModel", "A mers " + response.body()!!.string())
                }
                else {
                    Log.i("SearchViewModel", "Nu a mers")
                }
            }
        }
    }

    fun getDataSuccess() {
        _eventDataSuccess.value = true
    }

    fun getDataSuccessComplete() {
        _eventDataSuccess.value = false
    }
}