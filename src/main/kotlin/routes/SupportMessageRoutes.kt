package com.routes

import com.dtos.requests.supportMessage.SupportMessageRequestDto
import com.exceptions.AppException
import com.services.SupportMessageServices
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.supportMessageRoutes(
    service : SupportMessageServices
    ) {

    route("/support_messages") {
        authenticate ("auth_jwt") {
            post("/create") {
                val principal = call.principal<JWTPrincipal>()
                val role = principal?.getClaim("role" , String::class)
                if ( role == null ||(role != "customer" && role  != "admin")) throw AppException.UnauthorizedException("Invalid Request")

                val body = call.receive<SupportMessageRequestDto>()
                try {
                    service.createMessage(request = body , role = role)
                    call.respond(HttpStatusCode.OK)
                }catch (e : Exception) {
                    throw e
                }
            }
            get("{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                    ?: throw AppException.BadRequestException("Invalid Id")

                try {
                    val response = service.getMessages(ticketId = id) ?: emptyList()
                    call.respond(HttpStatusCode.OK , response)
                } catch (e : Exception) {
                    throw e
                }
            }
        }
    }

}