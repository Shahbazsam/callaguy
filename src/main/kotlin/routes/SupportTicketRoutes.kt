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
    service : SupportTicketService
) {
    route("/supportTicket") {
        authenticate ("auth_jwt"){
            post("/create") {
                val principal = call.principal<JWTPrincipal>()
                val id = principal?.getClaim("userId" , Int::class)
                val role = principal?.getClaim("role" , String::class)

                if (role != "customer" || id == null) throw AppException.ForbiddenException()
                val body = call.receive<SupportTicketRequestDto>()
                try {
                    service.createTicket(customerId = id, request = body)
                    call.respond(HttpStatusCode.OK , "Support Ticket Created Successfully")
                } catch (e : Exception) {
                    throw e
                }
            }
            get("/customer_tickets") {
                val principal = call.principal<JWTPrincipal>()
                val id = principal?.getClaim("userId" , Int::class)
                val role  = principal?.getClaim("role" , String::class)
                if (role != "customer" || id == null) throw AppException.ForbiddenException()

                try {
                    val response = service.customersTickets(id) ?: emptyList()
                    call.respond(HttpStatusCode.OK , response)
                }catch (e : Exception ) {
                    throw e
                }
            }
            post("update_status") {
                val principal = call.principal<JWTPrincipal>()
                val id = principal?.getClaim("userId" , Int::class)
                val role = principal?.getClaim("role" , String::class)

                if (role != "admin" || id == null) throw  AppException.ForbiddenException()

                try {
                    val body = call.receive<UpdateTicketStatusRequestDto>()
                   service.updateTicketStatus(body)
                    call.respond(HttpStatusCode.OK , "Status Updated Successfully")
                }catch (e : Exception) {
                    throw e
                }
            }
            get("/all_tickets") {
                val principal = call.principal<JWTPrincipal>()
                val id = principal?.getClaim("userId" , Int::class)
                val role = principal?.getClaim("role" , String::class)

                if (role != "admin" || id == null) throw  AppException.ForbiddenException()

                try {
                   val response =  service.allTicket() ?: emptyList()
                    call.respond(HttpStatusCode.OK , response)
                } catch (e : Exception) {
                    throw e
                }
            }
        }
    }
}