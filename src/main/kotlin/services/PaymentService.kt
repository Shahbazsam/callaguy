package com.services

import com.dtos.requests.payment.InitialPaymentRequestDto
import com.dtos.response.payment.PaymentResponseDto
import com.repository.PaymentRepository


interface PaymentService {
    suspend fun payment(request : InitialPaymentRequestDto) : Boolean
    suspend fun paymentInformation(requestId : Int) : PaymentResponseDto?
    suspend fun updatePaymentStatus(paymentId : Int ) : Boolean
}


class PaymentServiceImpl(
    private val repository: PaymentRepository
) : PaymentService {
    override suspend fun payment(request: InitialPaymentRequestDto): Boolean {
       return repository.createPayment(
            serviceRequestedId = request.serviceRequestId,
            amount = request.amount ,
        )
    }

    override suspend fun paymentInformation(requestId: Int): PaymentResponseDto? {
       val response = repository.getPaymentInformationByServiceRequestId(requestId)
        return response?.let { info ->
            PaymentResponseDto(
                id = info.id.value,
                amount = info.amount,
                status = info.paymentType,
                paymentDate = info.paymentDate,
                createdAt = info.createdAt
            )
        }
    }

    override suspend fun updatePaymentStatus(paymentId: Int): Boolean {
        return repository.updatePaymentStatus(paymentId)
    }
}