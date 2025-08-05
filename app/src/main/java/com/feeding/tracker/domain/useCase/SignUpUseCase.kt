package com.feeding.tracker.domain.useCase

import com.feeding.tracker.domain.repository.AuthRepository
import jakarta.inject.Inject

class SignUpUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) {
        suspend operator fun invoke(
            email: String,
            password: String,
        ) = authRepository.signUp(email, password)
    }
