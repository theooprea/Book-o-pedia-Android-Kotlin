package com.example.bookolx.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookolx.booklist.BooklistViewModel
import java.lang.IllegalArgumentException

class EditprofileViewModelFactory(private val token: String, private val username: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditprofileViewModel::class.java)) {
            return EditprofileViewModel(token, username) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}