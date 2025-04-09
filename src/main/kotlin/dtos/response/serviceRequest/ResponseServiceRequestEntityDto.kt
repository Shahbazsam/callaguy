package com.dtos.response.serviceRequest

import com.entities.ServiceRequestStatus
import io.ktor.network.sockets.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


@Serializable
data class ResponseServiceRequestEntityDto(
    val customerId : Int,
    val professionalId : Int ?,
    val subService : String ,
    val status : ServiceRequestStatus,
    @Contextual
    val preferredDate :LocalDate,
    @Contextual
    val preferredTime : LocalTime,
    val address: String,
    val specialInstructions : String?,
    @Contextual
    val createdAt : LocalDateTime
)
