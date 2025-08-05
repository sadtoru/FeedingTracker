package com.feeding.tracker.data.repository

import com.feeding.tracker.data.datasource.AuthFirebaseDataSource
import com.feeding.tracker.data.mappers.toDomain
import com.feeding.tracker.domain.model.UserDomain
import com.feeding.tracker.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val authDataSource: AuthFirebaseDataSource,
    ) : AuthRepository {
        override suspend fun login(
            email: String,
            password: String,
        ): Flow<Result<UserDomain>> = authDataSource.login(email, password)

        override suspend fun signUp(
            email: String,
            password: String,
        ): Flow<Result<UserDomain>> = authDataSource.signUp(email, password)

        override fun logout() {
            TODO("Not yet implemented")
        }

        override fun getCurrentUser(): Flow<UserDomain?> =
            authDataSource.currentUser.map { firebaseUser ->
                firebaseUser?.toDomain()
            }
    }
