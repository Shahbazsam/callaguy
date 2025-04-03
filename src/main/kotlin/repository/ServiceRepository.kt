package com.repository

import com.entities.ServiceEntity
import com.entities.Services
import org.jetbrains.exposed.sql.transactions.transaction

interface ServiceRepository {
    suspend fun findAllActiveServices() : List<ServiceEntity>
    suspend fun findById(serviceId : Int) : ServiceEntity?
    suspend fun createService(
        name : String,
        description : String
    ) : Boolean
    suspend fun deactivateService(serviceId : Int) : Boolean
}

class ServiceRepositoryImpl : ServiceRepository {
    override suspend fun findAllActiveServices(): List<ServiceEntity> {
        return transaction {
            ServiceEntity.find {
                Services.isActive eq true
            }.toList()
        }
    }

    override suspend fun findById(serviceId: Int): ServiceEntity?  {
        return transaction {
            ServiceEntity.findById(serviceId)
        }
    }

    override suspend fun createService(name: String, description: String): Boolean {
        try {
            transaction {
                ServiceEntity.new {
                    this.name = name
                    this.description = description
                }
            }
            return true
        } catch (e : Exception) {
            return false
        }
    }

    override suspend fun deactivateService(serviceId: Int): Boolean {
        try {
            transaction {
                ServiceEntity.findById(serviceId)?.apply {
                    isActive = false
                }
            }
            return true
        } catch (e : Exception) {
            return false
        }
    }
}