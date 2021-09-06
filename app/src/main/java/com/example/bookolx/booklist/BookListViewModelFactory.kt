package com.example.bookolx.booklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookolx.home.HomeViewModel
import java.lang.IllegalArgumentException

class BookListViewModelFactory(private val token: String, private val username: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookListViewModel::class.java)) {
            return BookListViewModel(token, username) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}