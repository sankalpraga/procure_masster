package com.techcognics.procuremasster.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id_token") val idToken: String
)
