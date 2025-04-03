package com.dtos.requests.services

import kotlinx.serialization.Serializable

@Serializable
data class ServiceRequest (
    val name : String,
    val description : String
    )