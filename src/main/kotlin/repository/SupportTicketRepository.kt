package com.repository

import com.dtos.response.supportTicket.SupportTicketResponse
import com.entities.*
import com.exceptions.AppException
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

interface SupportTicketRepository {
    suspend fun createTicket(
        customerId : Int,
        serviceRequestId : Int,
        issueType :String
    ) : Boolean
    suspend fun updateTicketStatus(ticketId : Int , status : Boolean) : Boolean
    suspend fun getAllTicket(): List<SupportTicketEntity>?
    suspend fun getAllTicketForCustomer(customerId : Int) : List<SupportTicketResponse>?
}

class SupportTicketRepositoryImpl : SupportTicketRepository {
    override suspend fun createTicket(customerId: Int, serviceRequestId : Int, issueType: String): Boolean {
        try {
            transaction {
                SupportTicketEntity.new {
                    customer = CustomerEntity[customerId]
                    serviceRequest = ServiceRequestEntity[serviceRequestId]
                    this.issueType = issueType
                    this.createdAt = LocalDateTime.now()
                }
            }
            return true
        }catch (e : Exception) {
            return false
        }
    }

    override suspend fun updateTicketStatus(ticketId: Int, status: Boolean): Boolean {
        try {

            transaction {
               SupportTicketEntity.findById(ticketId)?.apply {
                   this.status = SupportTicketStatus.RESOLVED
               }
            }
            return true
        }catch (e : Exception) {
            return false
        }
    }

    override suspend fun getAllTicket(): List<SupportTicketEntity>? {
        try {
            return transaction {
                SupportTicketEntity.all().toList()
            }
        }catch (e : Exception) {
            throw AppException.InternalServerError()
        }
    }

    override suspend fun getAllTicketForCustomer(customerId: Int): List<SupportTicketResponse>? {
        try {
            return transaction {
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
        } catch (e: Exception) {
            e.printStackTrace() // TEMP: log actual exception
            throw AppException.InternalServerError()
        }
    }
}