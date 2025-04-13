package com

import com.routes.*
import com.services.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    customerAuthService: CustomerAuthService,
    professionalAuthService: ProfessionalAuthService,
    serviceService: ServiceService,
    serviceRequest : ServiceRequestService,
    supportTicket : SupportTicketService,
    supportMessage : SupportMessageServices,
) {
    routing {
        customerAuthRoutes(customerAuthService)
        professionalAuthRoutes(professionalAuthService)
        serviceRoutes(serviceService)
        customerServiceRequestsRoutes(serviceRequest)
        professionalServiceRequestsRoute(serviceRequest)
        supportMessageRoutes(supportMessage)
        supportTicketRoutes(supportTicket)
    }
}
