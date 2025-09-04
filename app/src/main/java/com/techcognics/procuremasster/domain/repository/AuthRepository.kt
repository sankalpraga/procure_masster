package com.techcognics.procuremasster.domain.repository

import com.techcognics.procuremasster.data.AccountResponse
import com.techcognics.procuremasster.domain.model.AuthResult

interface AuthRepository {
    suspend fun login(username: String, password: String): String
    suspend fun getAccount(): AccountResponse

    suspend fun logout()

}
