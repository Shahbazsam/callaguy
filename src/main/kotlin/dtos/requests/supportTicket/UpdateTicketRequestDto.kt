package com.dtos.requests.supportTicket

import kotlinx.serialization.Serializable


@Serializable
data class UpdateTicketStatusRequestDto(
    val ticketId : Int ,
    val status : Boolean = true
)
