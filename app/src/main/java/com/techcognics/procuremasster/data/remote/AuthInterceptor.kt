package com.techcognics.procuremasster.data.remote

import android.util.Log
import com.techcognics.procuremasster.data.local.SessionManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        val token = runBlocking { sessionManager.getAuthToken().firstOrNull() }
        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
            Log.d("AuthInterceptor","Attaching token  to request: ${chain.request().url}")
            Log.d("AuthInterceptor","Token prefix: ${token.take(15)}")

        } else {
            Log.w("AuthInterceptor", "No token found for request for request: ${chain.request().url}")
        }

        val response = chain.proceed(requestBuilder.build())

        // ✅ If token expired → clear session
        if (response.code == 401) {
            Log.e("AuthInterceptor", "401 Unauthorized for: ${chain.request().url}, clearing session")
            runBlocking { sessionManager.clearSession() }
        }

        return response
    }
}
