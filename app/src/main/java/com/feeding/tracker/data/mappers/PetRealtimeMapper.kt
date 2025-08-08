package com.feeding.tracker.data.mappers

import com.feeding.tracker.data.model.PetRealtime
import com.feeding.tracker.domain.model.Pet

fun PetRealtime.toDomain(id: String) = Pet(id = id, name = name, idOwner = idOwner)

fun Pet.toData() = PetRealtime(name = name, idOwner = idOwner)
