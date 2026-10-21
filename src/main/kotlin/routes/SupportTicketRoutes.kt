package com.routes

import com.dtos.requests.supportTicket.SupportTicketRequestDto
import com.dtos.requests.supportTicket.UpdateTicketStatusRequestDto
import com.exceptions.AppException
import com.services.SupportTicketService
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.supportTicketRoutes(
    service: SupportTicketService
) {
    route("/support_ticket") {
        authenticate("auth_jwt") {
            post("/create") {
                val principal = call.principal<JWTPrincipal>()
                val id = principal?.getClaim("userId", Int::class)
                val role = principal?.getClaim("role", String::class)

                if (role != "customer" || id == null) throw AppException.ForbiddenException()
                val body = call.receive<SupportTicketRequestDto>()
                service.createTicket(customerId = id, request = body)

                call.respond(HttpStatusCode.OK, "Support Ticket Created Successfully")
            }
            get("/customer_tickets") {
                val principal = call.principal<JWTPrincipal>()
                val id = principal?.getClaim("userId", Int::class)
                val role = principal?.getClaim("role", String::class)
                if (role != "customer" || id == null) throw AppException.UnauthorizedException("unauthorized")

                val response = service.customersTickets(id) ?: emptyList()
                call.respond(HttpStatusCode.OK, response)
            }
            post("/update_status") {
                val principal = call.principal<JWTPrincipal>()
                val id = principal?.getClaim("userId", Int::class)
                val role = principal?.getClaim("role", String::class)

                if (role != "admin" || id == null) throw AppException.ForbiddenException()

                val body = call.receive<UpdateTicketStatusRequestDto>()
                service.updateTicketStatus(body)
                call.respond(HttpStatusCode.OK, "Status Updated Successfully")

            }
            get("/all_tickets") {
                val principal = call.principal<JWTPrincipal>()
                val id = principal?.getClaim("userId", Int::class)
                val role = principal?.getClaim("role", String::class)

                if (role != "admin" || id == null) throw AppException.ForbiddenException()
                val response = service.allTicket()
                call.respond(HttpStatusCode.OK, response)
            }
        }
    }
}