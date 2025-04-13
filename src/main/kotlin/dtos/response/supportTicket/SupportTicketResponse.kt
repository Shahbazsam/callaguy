package com.dtos.response.supportTicket

import com.entities.SupportTicketStatus
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class SupportTicketResponse(
    val customerId : Int,
    val serviceRequestId : Int,
    val issueType : String,
    val status : SupportTicketStatus,
    @Contextual
    val createdAt :LocalDateTime
)
