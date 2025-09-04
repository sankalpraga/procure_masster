package com.techcognics.procuremasster.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    @SerializedName("id_token")
    val idToken: String
)

data class AccountResponse(
    val id: Int,
    val login: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val imageUrl: String?,
    val activated: Boolean,
    val langKey: String,
    val createdBy: String,
    val lastModifiedBy: String,
    val lastModifiedDate: String,
    val authorities: List<String>,
    val password: String?,
    val subscriptionDate: String?
)
