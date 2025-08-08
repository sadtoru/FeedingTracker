package com.feeding.tracker.data.repository

import com.feeding.tracker.data.datasource.RealtimeFirebaseDataSource
import com.feeding.tracker.data.mappers.toData
import com.feeding.tracker.domain.model.UserDomain
import com.feeding.tracker.domain.repository.RealtimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RealtimeRepositoryImpl
    @Inject
    constructor(
        private val firebaseDataSource: RealtimeFirebaseDataSource,
    ) : RealtimeRepository {
        override fun addUserToDatabase(
            user: UserDomain,
            uid: String,
        ): Flow<Result<UserDomain>> = firebaseDataSource.addUserToDatabase(user.toData(), uid)
    }
