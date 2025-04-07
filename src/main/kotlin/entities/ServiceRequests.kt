package com.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.javatime.time


object ServiceRequests : IntIdTable("service_requests") {
    val customer = reference("customer_id" , Customers , onDelete = ReferenceOption.CASCADE)
    val professional = reference("professional_id", Professionals , onDelete = ReferenceOption.SET_NULL).nullable()
    val subService = reference("sub_service_id" , SubServices , onDelete = ReferenceOption.CASCADE)
    val status = enumerationByName("status", 20 , ServiceRequestStatus::class)
        .default(ServiceRequestStatus.REQUESTED)
    val preferredDate = date("preferred_date")
    val preferredTime = time("preferred_time")
    val address = varchar("address", 200)
    val specialInstructions = text("special_instructions").nullable()
    val createdAt = datetime("created_at")
}

class ServiceRequestEntity(id : EntityID<Int>) : IntEntity(id) {
    companion object: IntEntityClass<ServiceRequestEntity>(ServiceRequests)
    var customer by CustomerEntity referencedOn ServiceRequests.customer
    var professional by ProfessionalEntity optionalReferencedOn ServiceRequests.professional
    var subService by SubServiceEntity referencedOn ServiceRequests.subService
    var status by ServiceRequests.status
    var preferredDate by ServiceRequests.preferredDate
    var preferredTime by ServiceRequests.preferredTime
    var address by ServiceRequests.address
    var specialInstructions by ServiceRequests.specialInstructions
    var createdAt by ServiceRequests.createdAt
}



enum class ServiceRequestStatus {
    REQUESTED,
    ACCEPTED,
    COMPLETED,
    CANCELLED
}