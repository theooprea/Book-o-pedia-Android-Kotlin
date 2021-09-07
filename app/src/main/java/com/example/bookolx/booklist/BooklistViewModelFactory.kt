package com.example.bookolx.booklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class BooklistViewModelFactory(private val token: String, private val username: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BooklistViewModel::class.java)) {
            return BooklistViewModel(token, username) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}