package com.dtos.requests.serviceRequest

import kotlinx.serialization.Serializable


@Serializable
data class AcceptServiceRequestDto(
    val requestId : Int
)
