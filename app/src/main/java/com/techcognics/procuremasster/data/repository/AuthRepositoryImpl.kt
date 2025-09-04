package com.techcognics.procuremasster.data.repository

import android.util.Log
import com.techcognics.procuremasster.data.AccountResponse
import com.techcognics.procuremasster.data.local.SessionManager
import com.techcognics.procuremasster.data.remote.ApiService
import com.techcognics.procuremasster.data.remote.dto.LoginRequest
import com.techcognics.procuremasster.data.remote.dto.LoginResponse
import com.techcognics.procuremasster.domain.model.AuthResult
import com.techcognics.procuremasster.domain.model.User
import com.techcognics.procuremasster.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val sessionManager: SessionManager
) : AuthRepository {

    override suspend fun login(username: String, password: String): String {
        Log.d("AuthRepository", " Attempting Login For User : $username")

        val response: LoginResponse = api.login(LoginRequest(username, password))

        Log.d("AuthRepository","Token Received: ${response.idToken.take(20)}")
        //log only first part for safety

        sessionManager.saveSession(response.idToken, username) // ✅ must save token
        Log.d("AuthRepository", "Token Saved in Session manger $username")
        return response.idToken

    }


    override suspend fun getAccount(): AccountResponse {
        Log.d("AuthRepository", " fetching account details...")
        val account = api.getAccount()
        Log.d("AuthRepository", "Account fetched: ${account.login} with roles: ${account.authorities}")
        return account // ✅ will include token via AuthInterceptor
    }

    override suspend fun logout() {
        sessionManager.clearSession()
        }

}
