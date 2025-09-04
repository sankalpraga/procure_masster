package com.techcognics.procuremasster.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Extension property for DataStore
val Context.dataStore by preferencesDataStore(name = "session_prefs")

@Singleton
class SessionManager @Inject constructor(

    private val context: Context

) {
    companion object {
        private val KEY_TOKEN = stringPreferencesKey("auth_token")
        private val KEY_USER = stringPreferencesKey("username")
    }
    //Live Session expiry flag
    val sessionExpired = MutableStateFlow(false)

    suspend fun saveSession(token: String, username: String){
        context.dataStore.edit { prefs ->
        prefs[KEY_TOKEN] = token
        prefs[KEY_USER] = username
        }
        sessionExpired.value = false
    }

    fun getAuthToken(): Flow<String?> =
        context.dataStore.data.map { it[KEY_TOKEN] }

    fun getUsername(): Flow<String?> =
        context.dataStore.data.map { it[KEY_USER] }

    fun isLoggedin() : Flow<Boolean> =
        context.dataStore.data.map{ it[KEY_TOKEN] != null }


    suspend fun clearSession() {
        context.dataStore.edit { it.clear() }
        sessionExpired.value = true
    }

//    suspend fun saveAuthToken(token: String, username: String) {
//        context.dataStore.edit { prefs ->
//            prefs[KEY_TOKEN] = token
//            prefs[KEY_USER] = username
//        }
//    }




}
