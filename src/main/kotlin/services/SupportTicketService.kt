package com.services

import com.dtos.requests.supportTicket.SupportTicketRequestDto
import com.dtos.requests.supportTicket.UpdateTicketStatusRequestDto
import com.dtos.response.supportTicket.SupportTicketResponse
import com.repository.SupportTicketRepository

interface SupportTicketService {
    suspend fun createTicket(customerId : Int , request : SupportTicketRequestDto) : Unit
    suspend fun updateTicketStatus(request : UpdateTicketStatusRequestDto) : Unit
    suspend fun allTicket(): List<SupportTicketResponse>
    suspend fun customersTickets(customerId : Int) : List<SupportTicketResponse>?
}

class SupportTicketServiceImpl(
    private val repository: SupportTicketRepository
) : SupportTicketService {
    override suspend fun createTicket(customerId: Int, request: SupportTicketRequestDto): Unit {
        return repository.createTicket(
            customerId = customerId,
            serviceRequestId = request.serviceRequestId,
            issueType = request.issueType
        )
    }

    override suspend fun updateTicketStatus(request: UpdateTicketStatusRequestDto): Unit {
        return repository.updateTicketStatus(
            ticketId = request.ticketId,
            status = request.status
        )
    }

    override suspend fun allTicket(): List<SupportTicketResponse> {
        return repository.getAllTicket()
    }

    override suspend fun customersTickets(customerId: Int): List<SupportTicketResponse>? {
        return repository.getAllTicketForCustomer(customerId)
    }
}