package com.repository

import com.entities.ProfessionalEntity
import com.entities.Professionals
import com.entities.ServiceEntity
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

interface ProfessionalRepository {
    suspend fun findByEmail(email : String) : ProfessionalEntity?
    suspend fun findById(id : Int) : ProfessionalEntity?
    suspend fun createProfessional(
        username : String ,
        email : String ,
        passwordHash : String,
        experience : Int,
        documents : ByteArray,
        serviceIds : List<Int>
    ) : Boolean
}

class ProfessionalRepositoryImpl : ProfessionalRepository {
    override suspend fun findByEmail(email: String): ProfessionalEntity? {
        return transaction {
            ProfessionalEntity.find(Professionals.email eq email).firstOrNull()
        }
    }

    override suspend fun findById(id: Int): ProfessionalEntity? {
        return transaction {
            ProfessionalEntity.findById(id)
        }
    }

    override suspend fun createProfessional(
        username: String,
        email: String,
        passwordHash: String,
        experience: Int,
        documents: ByteArray,
        serviceIds : List<Int>
    ): Boolean {
        try {
            transaction {
               val professional = ProfessionalEntity.new {
                    this.username = username
                    this.email = email
                    this.passwordHash = passwordHash
                    this.experience = experience
                    this.documents = documents
                }
                val selectedServices = serviceIds.mapNotNull {
                    ServiceEntity.findById(it)
                }
                professional.services = SizedCollection(selectedServices)
            }
            return true
        } catch (e : Exception) {
            return false
        }
    }
}