package com.token

data class TokenConfig(
    val issuer : String,
    val audience : String,
    val expiresAt : Long,
)
