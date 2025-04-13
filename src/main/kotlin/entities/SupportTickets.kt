package com.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.datetime

object SupportTickets : IntIdTable("support_tickets") {
    val customer = reference("customer_id" , Customers , onDelete = ReferenceOption.CASCADE)
    val serviceRequest = reference("service_request_id" , ServiceRequests , onDelete = ReferenceOption.CASCADE)
    val issueType = varchar("issue_type" , 50)
    val status = enumerationByName("status" , 20 , SupportTicketStatus::class)
        .default(SupportTicketStatus.OPEN)
    val createdAt = datetime("created_at")
}

class SupportTicketEntity(id : EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SupportTicketEntity>(SupportTickets)
    var customer by CustomerEntity referencedOn SupportTickets.customer
    var serviceRequest by ServiceRequestEntity referencedOn SupportTickets.serviceRequest
    var issueType by SupportTickets.issueType
    var status by SupportTickets.status
    var createdAt by SupportTickets.createdAt

}


enum class SupportTicketStatus {
    OPEN,
    RESOLVED
}