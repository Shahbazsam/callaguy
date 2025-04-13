package com.repository

import com.entities.SupportMessageEntity
import com.entities.SupportMessageSender
import com.entities.SupportMessages
import com.entities.SupportTicketEntity
import com.exceptions.AppException
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

interface SupportMessageRepository {
    suspend fun createMessage(
        ticketId : Int ,
        role : String ,
        message : String
    ) : Boolean

    suspend fun getMessageByTicketId(ticketId : Int) : List<SupportMessageEntity>?
}

class SupportMessageRepositoryImpl : SupportMessageRepository {
    override suspend fun createMessage(ticketId: Int, role: String, message: String): Boolean {
        try {
            transaction {
                SupportMessageEntity.new {
                    ticket = SupportTicketEntity[ticketId]
                    this.sender = if (role == "customer") SupportMessageSender.CUSTOMER else SupportMessageSender.ADMIN
                    this.message = message
                    this.createdAt = LocalDateTime.now()
                }
            }
            return true
        } catch (e : Exception) {
            return false
        }
    }

    override suspend fun getMessageByTicketId(ticketId: Int): List<SupportMessageEntity>? {
        try {
            return transaction {
                SupportMessageEntity.find{
                    SupportMessages.ticket eq ticketId
                }.toList()
            }
        } catch (e : Exception) {
            throw AppException.InternalServerError()
        }
    }
}