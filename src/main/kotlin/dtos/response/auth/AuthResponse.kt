package com.dtos.response.auth

import kotlinx.serialization.Serializable


@Serializable
data class AuthResponse(
    val token : String,
)
