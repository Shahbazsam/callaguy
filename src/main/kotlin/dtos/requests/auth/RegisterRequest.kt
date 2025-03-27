package com.dtos.requests.auth
import kotlinx.serialization.Serializable


@Serializable
data class RegisterRequest(
    val userName : String,
    val email : String,
    val password: String,
    val userType : String,
    val phone : String,
    val address : String
)
