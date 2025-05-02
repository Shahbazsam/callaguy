package com.services

import com.dtos.response.services.ServiceResponse
import com.dtos.response.subService.SubServiceResponse
import com.repository.ServiceRepository
import com.repository.SubServiceRepository

interface ServiceService {
    suspend fun getAllServices(): List<ServiceResponse>
    suspend fun getSubServicesById(serviceId : Int) : List<SubServiceResponse>
}

class ServiceServiceImpl(
    private val serviceRepository: ServiceRepository,
    private val subServiceRepository: SubServiceRepository
) : ServiceService {
    override suspend fun getAllServices(): List<ServiceResponse> {
        return serviceRepository.findAllActiveServices().map { service ->
            ServiceResponse(
                id = service.id.value,
                name = service.name,
                description = service.description,
                imageUrl = service.imageUrl
            )
        }
    }

    override suspend fun getSubServicesById(serviceId: Int): List<SubServiceResponse> {
        return subServiceRepository.findByService(serviceId).map { subService ->
            SubServiceResponse(
                id = subService.id.value,
                name = subService.name,
                basePrice = subService.basePrice,
                visitCharge = subService.visitCharge,
                imageUrl = subService.imageUrl
            )
        }
    }
}