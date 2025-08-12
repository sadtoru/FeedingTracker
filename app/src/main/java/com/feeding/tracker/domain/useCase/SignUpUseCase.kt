package com.feeding.tracker.domain.useCase

import com.feeding.tracker.domain.model.UserDomain
import com.feeding.tracker.domain.repository.AuthRepository
import com.feeding.tracker.domain.repository.RealtimeRepository
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class SignUpUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        private val realtimeRepository: RealtimeRepository,
    ) {
        suspend operator fun invoke(
            email: String,
            password: String,
        ): Result<UserDomain> {
            val result = authRepository.signUp(email, password)
            return result.fold(
                onSuccess = { userDomain ->
                    val authUser = realtimeRepository.addUserToDatabase(userDomain, userDomain.uid!!)

                    if (authUser.isSuccess) {
                        return Result.success(userDomain)
                    }
                    Result.failure(Exception("Error al agregar el usuario a la base de datos"))
                },
                onFailure = {
                    Result.failure(it)
                },
            )
        }
    }
