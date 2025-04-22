package com.services

import com.repository.ProfilePictureRepository
import com.utils.Constants
import com.utils.save
import io.ktor.http.content.PartData
import java.io.File

interface ProfilePictureService {
    suspend fun updateCustomerProfilePicture(customerId : Int , file : PartData.FileItem) : Boolean
    suspend fun updateProfessionalProfilePicture(professionalId : Int , file : PartData.FileItem) : Boolean

}

class ProfilePictureServiceImpl(
    private val repository: ProfilePictureRepository
) : ProfilePictureService {
    override suspend fun updateCustomerProfilePicture(
        customerId: Int,
        file: PartData.FileItem
    ): Boolean {
        var fileName: String? = null
       return try {
            fileName = file.save(Constants.STATIC_CUSTOMER_PROFILE_PICTURE_PATH)
            val imageUrl = "${Constants.BASE_URL}${Constants.EXTERNAL_CUSTOMER_PROFILE_PICTURE_PATH}/$fileName"

            val updated = repository.updateCustomerProfilePicture(customerId = customerId , imageUrl = imageUrl)
            if (!updated) {
                File("${Constants.STATIC_CUSTOMER_PROFILE_PICTURE_PATH}/$fileName").delete()
            }
           updated
        }catch (e : Exception){
            if (fileName != null) {
                File("${Constants.STATIC_CUSTOMER_PROFILE_PICTURE_PATH}/$fileName").delete()
            }
           false
        }
    }

    override suspend fun updateProfessionalProfilePicture(professionalId: Int, file: PartData.FileItem): Boolean {
        var fileName: String? = null
        return try {
            fileName = file.save(Constants.STATIC_PROFESSIONAL_PROFILE_PICTURE_PATH)
            val imageUrl = "${Constants.BASE_URL}${Constants.EXTERNAL_PROFESSIONAL_PROFILE_PICTURE_PATH}/$fileName"

            val updated = repository.updateProfessionalProfilePicture(professionalId = professionalId , imageUrl = imageUrl)
            if (!updated) {
                File("${Constants.STATIC_PROFESSIONAL_PROFILE_PICTURE_PATH}/$fileName").delete()
            }
            updated
        }catch (e : Exception){
            if (fileName != null) {
                File("${Constants.STATIC_PROFESSIONAL_PROFILE_PICTURE_PATH}/$fileName").delete()
            }
            false
        }
    }
}