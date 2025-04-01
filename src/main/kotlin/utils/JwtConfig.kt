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

    private val secret  = System.getenv("JWR_SECRET")
    private val issuer = config.property("jwt.issuer").getString()
    private val audience = config.property("jwt.audience").getString()
    private val validityInMs = 36_000_000L * 24 * 365

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