package com.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import io.ktor.server.config.*
import java.util.Date

object JwtConfig {
    private lateinit var secret : String
    private lateinit var issuer : String
    private lateinit var audience : String
    private const val validityInMs = 36_000_000L * 24 * 365

    fun init(config : ApplicationConfig) {
        secret = System.getenv("JWT_SECRET")
        issuer = config.property("jwt.issuer").getString()
        audience = config.property("jwt.audience").toString()
    }

    fun generateToken(userId : Int , role : String) : String {
        return JWT.create()
            .withIssuer(issuer)
            .withAudience(audience)
            .withClaim("userId" , userId)
            .withClaim("role" , role)
            .withExpiresAt(getExpiration())
            .sign(Algorithm.HMAC256(secret))
    }

    fun getVerifier() : JWTVerifier {
        return JWT.require(Algorithm.HMAC256(secret))
            .withIssuer(issuer)
            .withAudience(audience)
            .build()
    }

    fun decodeToken(token : String) : DecodedJWT {
        return getVerifier().verify(token.replace("Bearer ", ""))
    }

    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)
}