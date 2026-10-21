package com.repository

import com.configuration.safeDbCall
import com.dtos.response.supportTicket.SupportTicketResponse
import com.entities.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

interface SupportTicketRepository {
    suspend fun createTicket(
        customerId: Int,
        serviceRequestId: Int,
        issueType: String
    ): Unit

    suspend fun updateTicketStatus(ticketId: Int, status: Boolean)
    suspend fun getAllTicket(): List<SupportTicketResponse>
    suspend fun getAllTicketForCustomer(customerId: Int): List<SupportTicketResponse>
}

class SupportTicketRepositoryImpl : SupportTicketRepository {
    override suspend fun createTicket(customerId: Int, serviceRequestId: Int, issueType: String): Unit = safeDbCall {
        transaction {
            SupportTicketEntity.new {
                customer = CustomerEntity[customerId]
                serviceRequest = ServiceRequestEntity[serviceRequestId]
                this.issueType = issueType
                this.createdAt = LocalDateTime.now()
            }
        }
    }

    override suspend fun updateTicketStatus(ticketId: Int, status: Boolean): Unit = safeDbCall {
        transaction {
            SupportTicketEntity.findById(ticketId)?.apply {
                this.status = SupportTicketStatus.RESOLVED
            }
        }
    }

    override suspend fun getAllTicket(): List<SupportTicketResponse> = safeDbCall {
        transaction {
            SupportTicketEntity.find {
                SupportTickets.status eq SupportTicketStatus.OPEN
            }.map { ticket ->
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

    override suspend fun getAllTicketForCustomer(customerId: Int): List<SupportTicketResponse> = safeDbCall {
        transaction {
            SupportTicketEntity.find {
                SupportTickets.customer eq customerId
            }.map { ticket ->
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
}