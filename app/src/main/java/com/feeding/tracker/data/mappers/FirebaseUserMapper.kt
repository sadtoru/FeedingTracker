package com.feeding.tracker.data.mappers

import com.feeding.tracker.domain.model.UserDomain
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toDomain() = UserDomain(
    uid = uid,
    displayName = displayName ?: "",
    email = email ?: "",
    photoUrl = photoUrl?.toString() ?: ""

)