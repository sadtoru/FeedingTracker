package com.feeding.tracker.domain.repository

import com.feeding.tracker.domain.model.UserDomain
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String,
    ): Flow<Result<UserDomain>>

    suspend fun signUp(
        email: String,
        password: String,
    ): Flow<Result<UserDomain>>

    fun logout()

    fun getCurrentUserId(): String?
}
