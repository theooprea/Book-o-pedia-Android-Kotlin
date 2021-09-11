package com.example.bookolx.addbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookolx.booklist.BooklistViewModel
import java.lang.IllegalArgumentException

class AddbookViewModelFactory(private val token: String, private val username: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddbookViewModel::class.java)) {
            return AddbookViewModel(token, username) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}