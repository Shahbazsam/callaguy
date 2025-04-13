package com.dtos.response.supportMessage

import com.entities.SupportMessageSender
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


@Serializable
data class SupportMessageResponseDto(
    val ticketId : Int ,
    val sender : SupportMessageSender,
    val message : String,
    @Contextual
    val createdAt : LocalDateTime
)
