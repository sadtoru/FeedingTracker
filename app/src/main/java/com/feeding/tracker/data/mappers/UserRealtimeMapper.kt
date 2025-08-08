package com.feeding.tracker.data.mappers

import com.feeding.tracker.data.model.UserRealtime
import com.feeding.tracker.domain.model.UserDomain

fun UserRealtime.toDomain(uid: String) =
    UserDomain(
        uid = uid,
        email = email,
        name = name,
        photoUrl = photoUrl,
    )
