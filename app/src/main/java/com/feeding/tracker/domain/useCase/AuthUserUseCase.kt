package com.feeding.tracker.domain.useCase

import com.feeding.tracker.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUserUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) {
        operator fun invoke() = authRepository.getCurrentUser()
    }
