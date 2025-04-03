package com.dtos.requests.subService

import kotlinx.serialization.Serializable


@Serializable
data class SubServiceRequest(
    val serviceId : Int
)
