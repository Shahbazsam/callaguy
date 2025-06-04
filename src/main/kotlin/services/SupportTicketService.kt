package com.services

import com.dtos.requests.supportTicket.SupportTicketRequestDto
import com.dtos.requests.supportTicket.UpdateTicketStatusRequestDto
import com.dtos.response.supportTicket.SupportTicketResponse
import com.repository.SupportTicketRepository

interface SupportTicketService {
    suspend fun createTicket(customerId : Int , request : SupportTicketRequestDto) : Boolean
    suspend fun updateTicketStatus(request : UpdateTicketStatusRequestDto) : Boolean
    suspend fun allTicket(): List<SupportTicketResponse>?
    suspend fun customersTickets(customerId : Int) : List<SupportTicketResponse>?
}

class SupportTicketServiceImpl(
    private val repository: SupportTicketRepository
) : SupportTicketService {
    override suspend fun createTicket(customerId: Int, request: SupportTicketRequestDto): Boolean {
        return repository.createTicket(
            customerId = customerId,
            serviceRequestId = request.serviceRequestId,
            issueType = request.issueType
        )
    }

    override suspend fun updateTicketStatus(request: UpdateTicketStatusRequestDto): Boolean {
        return repository.updateTicketStatus(
            ticketId = request.ticketId,
            status = request.status
        )
    }

    override suspend fun allTicket(): List<SupportTicketResponse>? {
        return repository.getAllTicket()?.map { ticket ->
            SupportTicketResponse(
                ticketId = ticket.id.value,
                customerId = ticket.customer.id.value,
                serviceRequestId = ticket.serviceRequest.id.value,
                issueType = ticket.issueType,
                status = ticket.status,
                createdAt = ticket.createdAt
            )
        }
    }

    override suspend fun customersTickets(customerId: Int): List<SupportTicketResponse>? {
        return repository.getAllTicketForCustomer(customerId)?.map { ticket ->
            SupportTicketResponse(
                ticketId = ticket.id.value,
                customerId = ticket.customer.id.value,
                serviceRequestId = ticket.serviceRequest.id.value,
                issueType = ticket.issueType,
                status = ticket.status,
                createdAt = ticket.createdAt
            )
        }
    }
}