package com.feeding.tracker.data.repository

import com.feeding.tracker.data.datasource.AuthFirebaseDataSource
import com.feeding.tracker.domain.model.UserDomain
import com.feeding.tracker.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(private val authDataSource: AuthFirebaseDataSource): AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Flow<Result<UserDomain>> {
        return authDataSource.login(email, password)
    }

    override suspend fun register(
        email: String,
        password: String
    ): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }

    override fun getCurrentUserId(): String? {
        TODO("Not yet implemented")
    }
}