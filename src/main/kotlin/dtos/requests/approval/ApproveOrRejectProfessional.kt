package com.dtos.requests.approval

import kotlinx.serialization.Serializable


@Serializable
data class ApproveOrRejectProfessional(
    val id : Int
)
