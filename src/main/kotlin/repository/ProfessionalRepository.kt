package com.repository

import com.dtos.response.profile.ProfessionalInfoDto
import com.entities.ProfessionalEntity
import com.entities.Professionals
import com.entities.ServiceEntity
import com.exceptions.AppException
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
        serviceIds : List<Int>
    ) : Boolean
    suspend fun isApproved(professionalId : Int) : Boolean
    suspend fun profileInfo(professionalId : Int) : ProfessionalInfoDto
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

    override suspend fun profileInfo(professionalId: Int): ProfessionalInfoDto {
         try {
             return transaction {
                ProfessionalEntity.find {
                    Professionals.id eq professionalId
                }.firstOrNull()?.let { professional ->
                    ProfessionalInfoDto(
                        userName = professional.username,
                        email = professional.email,
                        experience = professional.experience,
                        isApproved = professional.isApproved,
                        profilePicture = professional.profilePicture,
                        services = professional.services.map {
                            it.name
                        }
                    )
                } ?: throw AppException.NotFoundException("professional Not Found" , 1)

            }
        }catch (e : Exception) {
            throw AppException.InternalServerError()
        }
    }

    override suspend fun isApproved(professionalId: Int): Boolean {
        try {
            return transaction {
                ProfessionalEntity.findById(professionalId)?.isApproved == true
            }
        }catch (e : Exception){
            throw AppException.InternalServerError()
        }
    }

    override suspend fun createProfessional(
        username: String,
        email: String,
        passwordHash: String,
        experience: Int,
        serviceIds : List<Int>
    ): Boolean {
        try {
            transaction {
               val professional = ProfessionalEntity.new {
                    this.username = username
                    this.email = email
                    this.passwordHash = passwordHash
                    this.experience = experience
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