package com

import com.routes.authRoutes
import com.routes.serviceRoutes
import com.services.AuthService
import com.services.ServiceService
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*

fun Application.configureRouting(
    authService: AuthService,
    serviceService: ServiceService
) {
    routing {
        authRoutes(authService)
        serviceRoutes(serviceService)
    }
}
