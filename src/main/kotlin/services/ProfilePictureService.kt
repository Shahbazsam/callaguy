package com.services

import com.repository.ProfilePictureRepository

interface ProfilePictureService {
    suspend fun updateCustomerProfilePicture(customerId : Int , imageUrl : String )
    suspend fun updateProfessionalProfilePicture(professionalId : Int , imageUrl : String)

}

class ProfilePictureServiceImpl(
    private val repository: ProfilePictureRepository
) : ProfilePictureService {
    override suspend fun updateCustomerProfilePicture(
        customerId: Int,
        imageUrl : String
    ) {
        repository.updateCustomerProfilePicture(customerId , imageUrl)
    }

    override suspend fun updateProfessionalProfilePicture(professionalId: Int, imageUrl: String) {

        repository.updateProfessionalProfilePicture(professionalId = professionalId , imageUrl = imageUrl)
    }
}