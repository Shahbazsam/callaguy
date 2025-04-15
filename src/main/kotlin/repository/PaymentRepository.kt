package com.repository

import com.entities.PaymentEntity
import com.entities.PaymentStatus
import com.entities.Payments
import com.entities.ServiceRequestEntity
import com.exceptions.AppException
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal
import java.time.LocalDateTime

interface PaymentRepository {
    suspend fun createPayment(
        serviceRequestedId : Int ,
        amount : BigDecimal
    ) : Boolean

    suspend fun getPaymentInformationByServiceRequestId(requestId : Int) : PaymentEntity?
    suspend fun updatePaymentStatus(paymentId : Int) : Boolean
}

class PaymentRepositoryImpl : PaymentRepository {
    override suspend fun createPayment(
        serviceRequestedId: Int,
        amount: BigDecimal
    ): Boolean {
        try {
            transaction {
                PaymentEntity.new {
                   serviceRequest = ServiceRequestEntity[serviceRequestedId]
                   this.amount = amount
                    this.paymentDate = LocalDateTime.now()
                    this.paymentType = PaymentStatus.BOOKING
                    this.createdAt = LocalDateTime.now()
                }
            }
            return true
        } catch (e : Exception){
            return false
        }
    }

    override suspend fun getPaymentInformationByServiceRequestId(requestId: Int): PaymentEntity? {
        return try {
            transaction {
                PaymentEntity.find {
                    Payments.serviceRequest eq requestId
                }.firstOrNull()
            }
        }catch (e : Exception) {
            throw AppException.InternalServerError()
        }
    }

    override suspend fun updatePaymentStatus(paymentId: Int): Boolean {
        try {
            transaction {
                PaymentEntity.findById(paymentId)?.apply {
                    this.paymentType = PaymentStatus.COMPLETED
                }
            }
            return true
        }catch (e : Exception) {
            return false
        }
    }
}