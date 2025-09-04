package com.techcognics.procuremasster.data

import com.google.gson.annotations.SerializedName

data class AccountResponse(

    @SerializedName("id")
    val id: Int,

    @SerializedName("login")
    val login: String,

    @SerializedName("firstName")
    val firstName: String,

    @SerializedName("lastName")
    val lastName: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("imageUrl")
    val imageUrl: String?,

    @SerializedName("activated")
    val activated: Boolean,

    @SerializedName("langKey")
    val langKey: String,

    @SerializedName("createdBy")
    val createdBy: String,

    @SerializedName("lastModifiedBy")
    val lastModifiedBy: String,

    @SerializedName("lastModifiedDate")
    val lastModifiedDate: String,

    @SerializedName("authorities")
    val authorities: List<String>,

    @SerializedName("password")
    val password: String?,

    @SerializedName("subscriptionDate")
    val subscriptionDate: String?
)
