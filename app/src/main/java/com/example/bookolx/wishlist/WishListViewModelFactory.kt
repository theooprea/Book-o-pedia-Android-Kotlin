package com.example.bookolx.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookolx.booklist.BookListViewModel
import java.lang.IllegalArgumentException

class WishListViewModelFactory(private val token: String, private val username: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WishListViewModel::class.java)) {
            return WishListViewModel(token, username) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}