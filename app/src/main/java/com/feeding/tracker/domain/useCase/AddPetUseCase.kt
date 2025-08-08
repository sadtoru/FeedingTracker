package com.feeding.tracker.domain.useCase

import com.feeding.tracker.domain.model.Pet
import com.feeding.tracker.domain.model.UserDomain
import com.feeding.tracker.domain.repository.AuthRepository
import com.feeding.tracker.domain.repository.RealtimeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class AddPetUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        private val realtimeRepository: RealtimeRepository,
    ) {
        @OptIn(ExperimentalCoroutinesApi::class)
        operator fun invoke(petName: String): Flow<Result<Unit>> =
            authRepository
                .getCurrentUser()
                .take(1)
                .flatMapLatest { currentUser: UserDomain? ->
                    if (currentUser?.uid == null) {
                        flowOf(Result.failure(Exception("Usuario no autenticado.")))
                    } else {
                        val newPet =
                            Pet(
                                name = petName,
                                idOwner = currentUser.uid,
                            )
                        realtimeRepository.addPetToDatabase(newPet)
                    }
                }
    }
