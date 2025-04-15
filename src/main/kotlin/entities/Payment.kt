package com.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.datetime

object Payments : IntIdTable("payments") {
    val serviceRequest = reference("service_request_id" , ServiceRequests , onDelete = ReferenceOption.CASCADE)
    val amount = decimal("amount" , 10 , 2)
    val paymentType = enumerationByName("status" , 20 , PaymentStatus::class)
        .default(PaymentStatus.PENDING)
    val paymentDate = datetime("payment_date")
    val createdAt = datetime("created_at")
}

class PaymentEntity(id : EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PaymentEntity>(Payments)
    var serviceRequest by ServiceRequestEntity referencedOn Payments.serviceRequest
    var amount by Payments.amount
    var paymentType by Payments.paymentType
    var paymentDate by Payments.paymentDate
    var createdAt by Payments.createdAt
}


enum class PaymentStatus{
    PENDING,
    BOOKING,
    COMPLETED
}