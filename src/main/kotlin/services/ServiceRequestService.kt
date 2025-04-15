package com.services

import com.dtos.requests.serviceRequest.CreateServiceRequestDto
import com.dtos.response.serviceRequest.ResponseServiceRequestEntityDto
import com.entities.ServiceRequestStatus
import com.repository.ServiceRequestRepository

interface ServiceRequestService {
    suspend fun create(id : Int , request : CreateServiceRequestDto) : Boolean
    suspend fun getForCustomer(customerId : Int ) : List<ResponseServiceRequestEntityDto>?
    suspend fun getForProfessional(professionalId : Int ) : List<ResponseServiceRequestEntityDto>?
    suspend fun accept(professionalId: Int , requestId : Int) : Boolean
    suspend fun updateStatus(requestId: Int , newStatus : ServiceRequestStatus) : Boolean
    suspend fun getById(requestId : Int) : ResponseServiceRequestEntityDto?
}

class ServiceRequestServiceImpl(
    private val repository: ServiceRequestRepository
) : ServiceRequestService {
    override suspend fun create(id : Int ,request: CreateServiceRequestDto) : Boolean {
        return repository.createServiceRequest(
            customerId = id,
            subServiceId = request.subServiceId,
            preferredDate = request.preferredDate,
            preferredTime = request.preferredTime,
            address = request.address,
            specialInstruction = request.specialInstructions
        )
    }

    override suspend fun getForCustomer(customerId: Int): List<ResponseServiceRequestEntityDto>? {
        return repository.getServiceRequestByCustomer(customerId)?.map { request ->
            ResponseServiceRequestEntityDto(
                id = request.id.value,
                customerId = request.customer.id.value,
                professionalId = request.professional?.id?.value ,
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

    override suspend fun getForProfessional(professionalId: Int): List<ResponseServiceRequestEntityDto>? {
        return repository.getRequestedServiceRequestsForProfessional(professionalId)?.map { request ->
            ResponseServiceRequestEntityDto(
                id = request.id.value,
                customerId = request.customer.id.value,
                professionalId = request.professional?.id?.value ,
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

    override suspend fun accept(professionalId: Int, requestId: Int): Boolean {
        return repository.acceptServiceRequest(professionalId, requestId)
    }

    override suspend fun updateStatus(requestId: Int, newStatus: ServiceRequestStatus): Boolean {
        return repository.updateRequestStatus(requestId, newStatus)
    }

    override suspend fun getById(requestId: Int): ResponseServiceRequestEntityDto? {
        val entity = repository.getServiceRequestById(requestId)
        return entity?.let { request ->
            ResponseServiceRequestEntityDto(
                id = request.id.value,
                customerId = request.customer.id.value,
                professionalId = request.professional?.id?.value ,
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