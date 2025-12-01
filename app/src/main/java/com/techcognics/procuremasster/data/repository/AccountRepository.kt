package com.techcognics.procuremasster.data.repository

import com.techcognics.procuremasster.data.remote.ApiService
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun fetchAccount(token: String): com.techcognics.procuremasster.data.AccountResponse {
        return apiService.getAccount()
    }

}











