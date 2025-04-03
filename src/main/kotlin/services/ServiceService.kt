package com.services

import com.dtos.requests.services.ServiceRequest
import com.dtos.requests.subService.SubServiceRequest
import com.dtos.response.services.ServiceResponse
import com.dtos.response.subService.SubServiceResponse
import com.repository.ServiceRepository
import com.repository.SubServiceRepository
import jdk.jfr.Description
import java.math.BigDecimal

interface ServiceService {
    suspend fun getAllServices(): List<ServiceResponse>
    suspend fun getSubServicesById(serviceId : Int) : List<SubServiceResponse>
    suspend fun createServices(name : String , description: String): Boolean
    suspend fun createSubServices(serviceId: Int, name: String, basePrice: BigDecimal, visitCharge: BigDecimal): Boolean
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
                description = service.description
            )
        }
    }

    override suspend fun getSubServicesById(serviceId: Int): List<SubServiceResponse> {
        return subServiceRepository.findByService(serviceId).map { subService ->
            SubServiceResponse(
                id = subService.id.value,
                name = subService.name,
                basePrice = subService.basePrice,
                visitCharge = subService.visitCharge
            )
        }
    }

    override suspend fun createServices(name : String , description: String): Boolean {
        return serviceRepository.createService(
            name = name,
            description = description
        )
    }

    override suspend fun createSubServices(serviceId: Int, name: String, basePrice: BigDecimal , visitCharge: BigDecimal): Boolean {
        return subServiceRepository.createSubService(
            serviceId = serviceId,
            name = name,
            basePrice = basePrice,
            visitCharge = visitCharge
        )
    }
}