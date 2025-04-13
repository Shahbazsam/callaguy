package com

import com.configuration.configureAuth
import com.di.appModule
import com.exceptions.configureExceptionHandling
import com.services.*
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
        secret = System.getenv("JWT_SECRET")
    )


    JwtConfig.init(environment.config)


    install(Koin){
        modules(appModule)
    }
    val customerAuthService by inject<CustomerAuthService>()
    val professionalAuthService by inject<ProfessionalAuthService>()
    val serviceService by inject<ServiceService>()
    val serviceRequest by inject<ServiceRequestService>()
    val supportTicket by inject<SupportTicketService>()
    val supportMessage by inject<SupportMessageServices>()

    configureAuth(tokenConfig)
    configureExceptionHandling()
    configureSerialization()
    configureSwagger()
    configureDatabases()
    configureHTTP()
    configureRouting(
        customerAuthService = customerAuthService,
        professionalAuthService = professionalAuthService,
        serviceService = serviceService,
        serviceRequest = serviceRequest,
        supportTicket = supportTicket,
        supportMessage = supportMessage
    )
    configureMonitoring()
}
