package com

import com.routes.authRoutes
import com.routes.serviceRoutes
import com.services.CustomerAuthService
import com.services.ServiceService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    customerAuthService: CustomerAuthService,
    serviceService: ServiceService
) {
    routing {
        authRoutes(customerAuthService)
        serviceRoutes(serviceService)
    }
}
