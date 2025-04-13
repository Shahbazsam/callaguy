package com.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.datetime

object SupportMessages : IntIdTable("support_message") {
    val ticket = reference("ticket_id" ,SupportTickets , onDelete = ReferenceOption.CASCADE)
    val sender = enumerationByName("sender" , 20 , SupportMessageSender::class)
        .default(SupportMessageSender.CUSTOMER)
    val message = text("message")
    val createdAt = datetime("createdAt")
}

class SupportMessageEntity(id : EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SupportMessageEntity>(SupportMessages)
    var ticket by SupportTicketEntity referencedOn SupportMessages.ticket
    var sender by SupportMessages.sender
    var message by SupportMessages.message
    var createdAt by SupportMessages.createdAt
}


enum class SupportMessageSender {
    CUSTOMER,
    ADMIN
}
