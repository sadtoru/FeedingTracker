package com.feeding.tracker.domain.repository

import com.feeding.tracker.domain.model.Pet
import com.feeding.tracker.domain.model.UserDomain
import kotlinx.coroutines.flow.Flow

interface RealtimeRepository {
    fun addUserToDatabase(
        user: UserDomain,
        uid: String,
    ): Flow<Result<UserDomain>>

    fun addPetToDatabase(pet: Pet): Flow<Result<Unit>>
}
