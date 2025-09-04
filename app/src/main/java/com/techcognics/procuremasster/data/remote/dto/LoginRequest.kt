package com.techcognics.procuremasster.data.remote.dto

data class LoginRequest(

    val username: String,
    val password: String,
    val rememberMe: Boolean = true
    // your backend supports rememberMe

)
