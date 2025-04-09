package com.dtos.requests.serviceRequest

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalTime


@Serializable
data class CreateServiceRequestDto(
    val subServiceId : Int,
    @Contextual
    val preferredDate : LocalDate,
    @Contextual
    val preferredTime : LocalTime,
    val address : String,
    val specialInstructions : String?
)
