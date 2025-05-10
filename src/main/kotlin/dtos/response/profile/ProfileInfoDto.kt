package com.dtos.response.profile

import kotlinx.serialization.Serializable


@Serializable
data class ProfileInfoDto(
    val userName : String,
    val email : String,
    val address : String?,
    val phone : String? ,
    val profilePicture : String ?
)
