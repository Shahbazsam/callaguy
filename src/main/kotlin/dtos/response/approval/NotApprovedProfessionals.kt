package com.dtos.response.approval

import kotlinx.serialization.Serializable

@Serializable
data class NotApprovedProfessionals(
    val id : Int,
    val userName : String,
    val email : String,
    val experience : Int,
    val isApproved : Boolean,
    val documents : String?,
    val profilePicture : String?,
    val services : List<String>
)
