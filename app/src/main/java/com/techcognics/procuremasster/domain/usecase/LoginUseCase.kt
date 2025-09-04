package com.techcognics.procuremasster.domain.usecase

import com.techcognics.procuremasster.domain.model.AuthResult
import com.techcognics.procuremasster.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(

    private val repository: AuthRepository

) {

    suspend operator fun invoke(username: String, password: String): String {
        return repository.login(username, password)
    }

}
