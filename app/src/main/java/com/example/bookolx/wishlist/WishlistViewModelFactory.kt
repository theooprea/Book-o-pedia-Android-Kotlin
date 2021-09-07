package com.example.bookolx.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookolx.booklist.BooklistViewModel
import java.lang.IllegalArgumentException

class WishlistViewModelFactory(private val token: String, private val username: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WishlistViewModel::class.java)) {
            return WishlistViewModel(token, username) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}