package com.dtos.requests.supportTicket

import kotlinx.serialization.Serializable

@Serializable
data class SupportTicketRequestDto(
    val serviceRequestId : Int,
    val issueType : String
)
