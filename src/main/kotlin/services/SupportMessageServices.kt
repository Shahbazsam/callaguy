package com.services

import com.dtos.requests.supportMessage.SupportMessageRequestDto
import com.dtos.response.supportMessage.SupportMessageResponseDto
import com.repository.SupportMessageRepository

interface SupportMessageServices {
    suspend fun createMessage( request : SupportMessageRequestDto , role : String) : Boolean
    suspend fun getMessages (ticketId : Int ) : List<SupportMessageResponseDto>?
}

class SupportMessageServicesImpl(
    private val repository: SupportMessageRepository
) : SupportMessageServices {

    override suspend fun createMessage(request: SupportMessageRequestDto, role: String): Boolean {
        return repository.createMessage(
            ticketId = request.ticketId,
            role = role,
            message = request.message
        )
    }

    override suspend fun getMessages(ticketId: Int): List<SupportMessageResponseDto>? {
        return repository.getMessageByTicketId(ticketId = ticketId)?.map { response ->
            SupportMessageResponseDto(
                ticketId = response.ticket.id.value,
                sender = response.sender,
                message = response.message,
                createdAt = response.createdAt
            )
        }
    }
}