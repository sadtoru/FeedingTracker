package com.feeding.tracker.domain.useCase

import com.feeding.tracker.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) = repository.login(email, password)

}