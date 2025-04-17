package com.routes

import com.exceptions.AppException
import com.services.ServiceService
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.serviceRoutes(
    serviceService: ServiceService
) {
    route("/services"){
        authenticate("auth_jwt") {
            get {
                val principal = call.principal<JWTPrincipal>()
                val role = principal?.getClaim("role" , String::class)
                //if (role != "professional") throw AppException.BadRequestException("Wrong individual")

                try {
                    val services = serviceService.getAllServices()
                    call.respond(HttpStatusCode.OK , services)

                } catch (e : Exception ) {
                   throw AppException.InternalServerError()
                }
            }

            get("/{id}") {
                try {
                    val serviceId = call.parameters["id"]?.toIntOrNull()
                        ?: throw AppException.BadRequestException("Invalid Service Id ")

                    val services = serviceService.getSubServicesById(serviceId)
                    call.respond(HttpStatusCode.OK , services)
                } catch (e : Exception) {
                    throw AppException.InternalServerError()
                }
            }
        }
    }
}