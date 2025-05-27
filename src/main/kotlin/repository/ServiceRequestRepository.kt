package com.repository

import com.dtos.response.serviceRequest.ResponseServiceRequestEntityDto
import com.entities.*
import com.exceptions.AppException
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

interface ServiceRequestRepository {

    suspend fun createServiceRequest(
        customerId : Int,
        subServiceId : Int,
        preferredDate : LocalDate,
        preferredTime : LocalTime,
        address : String ,
        specialInstruction :String ?
    ) :Boolean

    suspend fun getRequestedServiceRequestsForProfessional(professionalId : Int) : List<ServiceRequestEntity>?
    suspend fun acceptServiceRequest(professionalId: Int , requestId : Int ) :Boolean
    suspend fun updateRequestStatus(requestId: Int , newStatus : ServiceRequestStatus) : Boolean
    suspend fun getServiceRequestByCustomer(customerId: Int) : List<ResponseServiceRequestEntityDto>?
    suspend fun getServiceRequestById(requestId : Int) : ServiceRequestEntity?
}

class ServiceRequestRepositoryImpl : ServiceRequestRepository {
    override suspend fun createServiceRequest(
        customerId: Int,
        subServiceId: Int,
        preferredDate: LocalDate,
        preferredTime: LocalTime,
        address: String,
        specialInstruction: String?
    ): Boolean {
        try {
            transaction {
                ServiceRequestEntity.new {
                    customer = CustomerEntity[customerId]
                    subService = SubServiceEntity[subServiceId]
                    this.preferredDate = preferredDate
                    this.preferredTime = preferredTime
                    this.address = address
                    this.specialInstructions = specialInstruction
                    createdAt = LocalDateTime.now()
                }
            }
            return true
        }catch (e : Exception) {
            return false
        }
    }

    override suspend fun getRequestedServiceRequestsForProfessional(professionalId: Int): List<ServiceRequestEntity>? {
        try {
            return transaction {
                val offeredServices = ProfessionalEntity[professionalId].services.map { it.id.value }

                ServiceRequestEntity.find {
                    ServiceRequests.status eq ServiceRequestStatus.REQUESTED
                }.filter { request ->
                    val parentServiceId = request.subService.serviceId.value
                    parentServiceId in offeredServices
                }
            }
        } catch (e : Exception) {
            throw AppException.InternalServerError()
        }
    }

    override suspend fun acceptServiceRequest(professionalId: Int, requestId: Int): Boolean {
        return try {
            transaction {
                val request = ServiceRequestEntity.findById(requestId)
                val professional = ProfessionalEntity.findById(professionalId)
                if (request != null && professional != null && request.status == ServiceRequestStatus.REQUESTED && professional.services.map { it.id.value }.contains(request.subService.serviceId.value)) {

                    request.status = ServiceRequestStatus.ACCEPTED
                    request.professional = professional
                    true
                }else {
                    false
                }
            }
        }catch (e : Exception) {
            throw AppException.ForbiddenException()
        }
    }

    override suspend fun updateRequestStatus(requestId: Int, newStatus: ServiceRequestStatus): Boolean {
        return try {
            transaction {
                val request = ServiceRequestEntity.findById(requestId)
                if (request != null ) {
                    request.status = newStatus
                    true
                }else {
                    false
                }
            }
        }catch (e : Exception) {
            throw AppException.ForbiddenException()
        }
    }

    override suspend fun getServiceRequestByCustomer(customerId: Int): List<ResponseServiceRequestEntityDto>? {
        return newSuspendedTransaction {
            ServiceRequestEntity.find {
                ServiceRequests.customer eq customerId
            }.map { request ->
                ResponseServiceRequestEntityDto(
                    id = request.id.value,
                    customerId = request.customer.id.value,
                    professionalId = request.professional?.id?.value,
                    amount = request.subService.basePrice,
                    subService = request.subService.name,
                    subServiceId = request.subService.id.value,
                    status = request.status,
                    preferredDate = request.preferredDate,
                    preferredTime = request.preferredTime,
                    address = request.address,
                    specialInstructions = request.specialInstructions,
                    createdAt = request.createdAt
                )
            }
        }

    }

    override suspend fun getServiceRequestById(requestId: Int): ServiceRequestEntity? {
        return try {
            transaction {
                ServiceRequestEntity.findById(requestId)
            }
        }catch (e :Exception) {
            throw AppException.InternalServerError()
        }
    }
}