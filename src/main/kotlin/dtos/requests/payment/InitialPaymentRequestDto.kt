package com.dtos.requests.payment

import com.entities.PaymentStatus
import com.utils.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal


@Serializable
data class InitialPaymentRequestDto(
    val serviceRequestId : Int,
    @Serializable(with = BigDecimalSerializer::class)
    val amount : BigDecimal
)
