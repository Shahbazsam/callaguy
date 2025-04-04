package com

import com.configuration.configureAuth
import com.di.appModule
import com.services.CustomerAuthService
import com.services.ServiceService
import com.token.TokenConfig
import com.utils.JwtConfig
import io.ktor.server.application.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresAt = 365L * 1000L * 60L * 60L * 24L,
    )


    JwtConfig.init(environment.config)


    install(Koin){
        modules(appModule)
    }
    val authService by inject<CustomerAuthService>()
    val serviceService by inject<ServiceService>()

    configureAuth(tokenConfig)
    configureSerialization()
    configureDatabases()
    configureHTTP()
    configureRouting(
        customerAuthService =  authService ,
        serviceService = serviceService
    )
    configureMonitoring()
}
