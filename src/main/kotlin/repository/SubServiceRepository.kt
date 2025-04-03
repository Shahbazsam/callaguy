package com.repository

import com.entities.ServiceEntity
import com.entities.SubServiceEntity
import com.entities.SubServices
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal

interface SubServiceRepository {
    suspend fun findByService(serviceId : Int) : List<SubServiceEntity>
    suspend fun findBYId(subServiceId : Int): SubServiceEntity?
    suspend fun createSubService(
        serviceId: Int,
        name : String,
        basePrice : BigDecimal,
        visitCharge : BigDecimal
    ) : Boolean
}

class SubServiceRepositoryImpl : SubServiceRepository {
    override suspend fun findByService(serviceId: Int): List<SubServiceEntity> {
        return transaction {
            SubServiceEntity. find {
                SubServices.serviceId eq serviceId
            }.toList()
        }
    }

    override suspend fun findBYId(subServiceId: Int): SubServiceEntity? {
        return transaction {
            SubServiceEntity.findById(subServiceId)
        }
    }

    override suspend fun createSubService(serviceId: Int, name: String, basePrice: BigDecimal , visitCharge: BigDecimal): Boolean {
        try {
            transaction {
                SubServiceEntity.new {
                    this.serviceId = ServiceEntity[serviceId].id
                    this.name = name
                    this.basePrice = basePrice
                    this.visitCharge = visitCharge
                }
            }
            return true
        } catch (e :Exception) {
            return false
        }
    }
}