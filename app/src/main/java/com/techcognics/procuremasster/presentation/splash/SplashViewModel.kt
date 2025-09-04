package com.techcognics.procuremasster.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techcognics.procuremasster.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

    private val authRepository: AuthRepository

) : ViewModel() {

    private val _startDestination = MutableStateFlow("login")
    val startDestination: StateFlow<String> = _startDestination

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {
            try {
                val account = authRepository.getAccount() // ✅ calls /api/account
                when {
                    account.authorities.contains("ROLE_SUPPLIER") ->
                        _startDestination.value = "supplier_dashboard"
                    account.authorities.contains("ROLE_BUYER") ->
                        _startDestination.value = "buyer_dashboard"
                    account.authorities.contains("ROLE_ADMIN") ->
                        _startDestination.value = "admin_dashboard"
                    else -> _startDestination.value = "login"
                }
            } catch (e: Exception) {
                _startDestination.value = "login" // no/invalid token
            }
        }
    }
}





