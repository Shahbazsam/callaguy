package com

import com.configuration.DatabaseFactory
import com.configuration.configureAuth
import com.repository.UserRepositoryImpl
import com.services.AuthService
import com.services.AuthServiceImpl
import com.token.TokenConfig
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val repository = UserRepositoryImpl()
    val authService = AuthServiceImpl(repository)
    DatabaseFactory.init()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresAt = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )
    configureAuth(tokenConfig)
    configureSerialization()
    configureDatabases()
    configureHTTP()
    configureRouting(authService)
}
