package com.example.bookolx.editbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class EditbookViewModelFactory(private val token: String, private val username: String, private val title: String,
                               private val author: String, private val genre: String, private val pages: Int,
                               private val price: Float, private val quantity: Int,) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditbookViewModel::class.java)) {
            return EditbookViewModel(token, username, title, author, genre, pages, price, quantity) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}