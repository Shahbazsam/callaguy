package com.routes

import com.exceptions.AppException
import com.services.ServiceService
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.serviceRoutes(
    serviceService: ServiceService
) {
    route("/services"){
        authenticate("auth-jwt") {
            get {
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
                } catch (e : AppException) {
                    throw e
                } catch (e : Exception) {
                    throw AppException.InternalServerError()
                }
            }
        }
    }
}