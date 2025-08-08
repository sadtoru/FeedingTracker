package com.feeding.tracker.data.mappers

import com.feeding.tracker.data.model.UserRealtime
import com.feeding.tracker.domain.model.UserDomain
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toDomain() =
    UserDomain(
        uid = uid,
        name = displayName ?: "",
        email = email ?: "",
        photoUrl = photoUrl?.toString() ?: "",
    )

fun UserDomain.toData() =
    UserRealtime(
        email = email,
        name = name,
        photoUrl = photoUrl ?: "",
    )
