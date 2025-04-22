package com.repository

import com.entities.CustomerEntity
import com.entities.ProfessionalEntity
import com.exceptions.AppException
import com.utils.Constants
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import kotlin.text.substringAfterLast

interface ProfilePictureRepository {
    suspend fun updateCustomerProfilePicture(customerId : Int , imageUrl : String ) : Boolean
    suspend fun updateProfessionalProfilePicture(professionalId : Int , imageUrl : String ) : Boolean
}


class ProfilePictureRepositoryImpl : ProfilePictureRepository {
    override suspend fun updateCustomerProfilePicture(customerId: Int, imageUrl: String): Boolean {
        return try {
            transaction {
                val customer = CustomerEntity.findById(customerId) ?: throw AppException.BadRequestException("User Not found")

                val oldImageUrl = customer.profilePicture
                if (!oldImageUrl.isNullOrBlank()) {
                    val oldFileName = oldImageUrl.substringAfterLast("/")
                    val file = File("${Constants.STATIC_CUSTOMER_PROFILE_PICTURE_PATH}/$oldFileName")
                    if (file.exists()) {
                        file.delete()
                    }
                }
                customer.profilePicture = imageUrl
            }
            true
        }catch (e : Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun updateProfessionalProfilePicture(professionalId: Int, imageUrl: String): Boolean {
        return try {
            transaction {
                val professional = ProfessionalEntity.findById(professionalId) ?: throw AppException.BadRequestException("User Not found")

                val oldImageUrl =  professional.profilePicture
                if (!oldImageUrl.isNullOrBlank()) {
                    val oldFileName = oldImageUrl.substringAfterLast("/")
                    val file = File("${Constants.STATIC_PROFESSIONAL_PROFILE_PICTURE_PATH}/$oldFileName")
                    if (file.exists()) {
                        file.delete()
                    }
                }
                professional.profilePicture = imageUrl
            }
            true
        }catch (e : Exception) {
            e.printStackTrace()
            false
        }
    }
}