package com.example.bookolx.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookolx.booklist.BookListViewModel
import com.example.bookolx.home.HomeViewModel
import java.lang.IllegalArgumentException

class EditProfileViewModelFactory(private val token: String, private val username: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            return EditProfileViewModel(token, username) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}