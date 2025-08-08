package com.feeding.tracker.data.datasource

import com.feeding.tracker.data.mappers.FirebaseUserDTO
import com.feeding.tracker.data.mappers.toDomain
import com.feeding.tracker.domain.model.UserDomain
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class RealtimeFirebaseDataSource {
    private var database = Firebase.database.reference

    fun addUserToDatabase(
        user: FirebaseUserDTO,
        uid: String,
    ): Flow<Result<UserDomain>> =
        flow {
            try {
                database
                    .child("users")
                    .child(uid)
                    .setValue(user)
                    .await()
                emit(Result.success(user.toDomain(uid)))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
}
