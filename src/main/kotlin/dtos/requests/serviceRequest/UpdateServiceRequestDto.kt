package com.dtos.requests.serviceRequest

import com.entities.ServiceRequestStatus
import kotlinx.serialization.Serializable


@Serializable
data class UpdateServiceRequestDto(
    val requestId : Int ,
    val newStatus : ServiceRequestStatus
)
