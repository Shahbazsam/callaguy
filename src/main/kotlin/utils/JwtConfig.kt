package com.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import io.ktor.server.config.*
import java.util.Date

class JwtConfig() {

    companion object {
        lateinit var config : ApplicationConfig
        fun init(config : ApplicationConfig) {
            this.config = config
        }
    }

    private val issuer = config.property("jwt.issuer").getString()
    private val audience = config.property("jwt.audience").getString()
    private val validityInMs = 365L * 1000L * 60L * 60L * 24L

    fun generateToken(userId : Int , role : String) : String {

        return JWT.create()
            .withIssuer(issuer)
            .withAudience(audience)
            .withClaim("userId" , userId)
            .withClaim("role" , role)
            .withExpiresAt(getExpiration())
            .sign(Algorithm.HMAC256(getSecret()))
    }

    fun getVerifier() : JWTVerifier {
        return JWT.require(Algorithm.HMAC256(getSecret()))
            .withIssuer(issuer)
            .withAudience(audience)
            .build()
    }
    private fun getSecret(): String {
        return System.getenv("JWT_SECRET") ?: throw IllegalStateException("JWT_SECRET is not set")
    }


    fun decodeToken(token : String) : DecodedJWT {
        return getVerifier().verify(token.replace("Bearer ", ""))
    }

    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)
}