package com.dtos.response.serviceRequest

import com.entities.ServiceRequestStatus
import com.utils.BigDecimalSerializer
import io.ktor.network.sockets.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


@Serializable
data class ResponseServiceRequestEntityDto(
    val id : Int,
    val customerId : Int,
    val professionalId : Int ?,
    @Serializable(with = BigDecimalSerializer::class)
    val amount : BigDecimal,
    val subService : String ,
    val subServiceId : Int ,
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
