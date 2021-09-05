package com.example.bookolx.home

import androidx.lifecycle.ViewModel

class HomeViewModel(tokenArg: String, emailArg: String, usernameArg: String) : ViewModel() {
    var token = tokenArg
    var email = emailArg
    var username = usernameArg
}