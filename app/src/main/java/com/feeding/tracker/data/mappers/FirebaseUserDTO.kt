package com.feeding.tracker.data.mappers

import com.feeding.tracker.domain.model.UserDomain

data class FirebaseUserDTO(
    val email: String = "",
    val name: String = "",
    val photoUrl: String? = null,
)

fun FirebaseUserDTO.toDomain(uid: String) =
    UserDomain(
        uid = uid,
        email = email,
        name = name,
        photoUrl = photoUrl,
    )
