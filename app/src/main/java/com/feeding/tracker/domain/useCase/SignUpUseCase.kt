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
        //        suspend operator fun invoke(
//            email: String,
//            password: String,
//        ): Flow<Result<UserDomain>> =
//            flow {
//                authRepository.signUp(email, password).collect { result ->
//                    result
//                        .onSuccess { userDomain ->
//                            val userDomainResult =
//                                realtimeRepository.addUserToDatabase(userDomain).first()
//                            if (userDomainResult.isSuccess) {
//                                emit(Result.success(userDomain))
//                            } else {
//                                emit(Result.failure(userDomainResult.exceptionOrNull()!!))
//                            }
//                        }.onFailure { exception ->
//                            emit(Result.failure(exception))
//                        }
//                }
//            }

        @OptIn(ExperimentalCoroutinesApi::class)
        suspend operator fun invoke(
            email: String,
            password: String,
        ): Flow<Result<UserDomain>> =
            authRepository.signUp(email, password).flatMapConcat { result ->
                result.fold(
                    onSuccess = { userDomain ->
                        realtimeRepository.addUserToDatabase(userDomain, userDomain.uid!!).map { dbResult ->
                            if (dbResult.isSuccess) {
                                Result.success(userDomain)
                            } else {
                                Result.failure(dbResult.exceptionOrNull()!!)
                            }
                        }
                    },
                    onFailure = {
                        flowOf(Result.failure(it))
                    },
                )
            }
    }
