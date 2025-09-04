package com.techcognics.procuremasster.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techcognics.procuremasster.data.AccountResponse
import com.techcognics.procuremasster.domain.repository.AuthRepository
import com.techcognics.procuremasster.presentation.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<AccountResponse>>(UiState.Idle)
    val uiState: StateFlow<UiState<AccountResponse>> = _uiState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                repo.login(username, password)
                val account = repo.getAccount()
                if (account.authorities.contains("ROLE_SUPPLIER")) {
                    _uiState.value = UiState.Success(account)
                } else {
                    _uiState.value = UiState.Error("Invalid credentials: Only Supplier role allowed")
                    repo.logout()
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Login failed")
            }
        }
    }
}
