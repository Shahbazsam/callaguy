package com

import com.routes.customerAuthRoutes
import com.routes.professionalAuthRoutes
import com.routes.serviceRoutes
import com.services.CustomerAuthService
import com.services.ProfessionalAuthService
import com.services.ServiceService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    customerAuthService: CustomerAuthService,
    professionalAuthService: ProfessionalAuthService,
    serviceService: ServiceService
) {
    routing {
        customerAuthRoutes(customerAuthService)
        professionalAuthRoutes(professionalAuthService)
        serviceRoutes(serviceService)
    }
}
