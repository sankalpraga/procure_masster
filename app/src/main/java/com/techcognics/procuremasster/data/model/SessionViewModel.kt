package com.techcognics.procuremasster.data.model

import androidx.lifecycle.ViewModel
import com.techcognics.procuremasster.data.repository.AccountRepository
import com.techcognics.procuremasster.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    suspend fun loadUserFromToken(token: String) {
        val accountResponse = accountRepository.fetchAccount(token)
        _user.value = User(userId = accountResponse.id.toString(), token = token)
    }

    fun logout() {
        _user.value = null
    }

    fun setUser(user: User) {
        _user.value = user
    }
}
