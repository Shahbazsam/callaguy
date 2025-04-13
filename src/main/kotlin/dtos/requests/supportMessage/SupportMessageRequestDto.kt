package com.dtos.requests.supportMessage

import kotlinx.serialization.Serializable


@Serializable
data class SupportMessageRequestDto(
    val ticketId : Int,
    val message : String
)
