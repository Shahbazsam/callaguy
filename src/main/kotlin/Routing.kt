package com

import com.routes.*
import com.services.*
import com.utils.Constants
import io.ktor.server.application.*
import io.ktor.server.http.content.staticFiles
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting(
    customerAuthService: CustomerAuthService,
    professionalAuthService: ProfessionalAuthService,
    serviceService: ServiceService,
    serviceRequest : ServiceRequestService,
    supportTicket : SupportTicketService,
    supportMessage : SupportMessageServices,
    payment : PaymentService
) {
    routing {

        staticFiles(remotePath = Constants.EXTERNAL_CUSTOMER_PROFILE_PICTURE_PATH , dir = File(Constants.STATIC_CUSTOMER_PROFILE_PICTURE_PATH))
        profilePicture()
        customerAuthRoutes(customerAuthService)
        professionalAuthRoutes(professionalAuthService)
        serviceRoutes(serviceService)
        customerServiceRequestsRoutes(serviceRequest)
        professionalServiceRequestsRoute(serviceRequest)
        supportMessageRoutes(supportMessage)
        supportTicketRoutes(supportTicket)
        paymentRoutes(payment)
    }
}
