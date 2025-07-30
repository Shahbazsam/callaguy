package com.dtos.response.profile

import kotlinx.serialization.Serializable


@Serializable
data class ProfessionalInfoDto(
    val userName : String,
    val email : String,
    val experience : Int,
    val isApproved : Boolean,
    val profilePicture : String?,
    val services : List<String>
)
