package com.example.bookolx.searchpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookolx.booklist.BookListViewModel
import java.lang.IllegalArgumentException

class SearchPageViewModelFactory(private val token: String, private val username: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchPageViewModel::class.java)) {
            return SearchPageViewModel(token, username) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}