package com.techcognics.procuremasster.data.remote

data class User(
    val activated: Boolean,
    val createdBy: String,
    val createdDate: String,
    val email: String,
    val firstName: String,
    val id: Int,
    val imageUrl: Any,
    val langKey: String,
    val lastModifiedBy: String,
    val lastModifiedDate: String,
    val lastName: String,
    val login: String,
    val mobileNumber: String,
    val password: String,
    val phoneNumber: String,
    val resetDate: Any,
    val subscriptionDate: Any
)