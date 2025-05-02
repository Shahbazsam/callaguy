package com.dtos.response.subService

import com.utils.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal


@Serializable
data class SubServiceResponse(
    val id : Int ,
    val name : String,
    @Serializable(with = BigDecimalSerializer::class)
    val basePrice : BigDecimal,
    @Serializable(with = BigDecimalSerializer::class)
    val visitCharge : BigDecimal,
    val imageUrl : String? = null
)
