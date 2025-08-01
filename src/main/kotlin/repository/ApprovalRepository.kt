package com.repository

import com.configuration.safeDbCall
import com.dtos.response.approval.NotApprovedProfessionals
import com.entities.ProfessionalEntity
import com.entities.Professionals
import com.exceptions.AppException
import org.jetbrains.exposed.sql.transactions.transaction


interface ApprovalRepository {
    suspend fun getNotApprovedProfessional(): List<NotApprovedProfessionals>
    suspend fun approveProfessional(id : Int)
    suspend fun rejectProfessional(id: Int)
}


class ApprovalRepositoryImpl() : ApprovalRepository {
    override suspend fun getNotApprovedProfessional(): List<NotApprovedProfessionals> = safeDbCall {
        transaction {
            ProfessionalEntity.find {
                Professionals.isApproved eq false
            }.map { professional ->
                NotApprovedProfessionals(
                    id = professional.id.value,
                    userName = professional.username,
                    email = professional.email,
                    experience = professional.experience,
                    isApproved = professional.isApproved,
                    profilePicture = professional.profilePicture,
                    services = professional.services.map {
                        it.name
                    }
                )
            }
        }
    }

    override suspend fun approveProfessional(id: Int) : Unit = safeDbCall {
        transaction {
            val professional = ProfessionalEntity.findById(id)
                ?: throw AppException.NotFoundException("Professional Not Found", id)
            professional.isApproved = true
        }
    }

    override suspend fun rejectProfessional(id: Int) : Unit = safeDbCall {
        transaction {
            val professional = ProfessionalEntity.findById(id)
                ?: throw AppException.NotFoundException("Professional Not Found", id)
            professional.delete()
        }
    }
}