package com.dtos.response.payment

import com.entities.PaymentStatus
import com.utils.BigDecimalSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime

@Serializable
data class PaymentResponseDto(
    val id : Int,
    @Serializable(with = BigDecimalSerializer::class)
    val amount : BigDecimal,
    val status : PaymentStatus,
    @Contextual
    val paymentDate : LocalDateTime,
    @Contextual
    val createdAt : LocalDateTime
)