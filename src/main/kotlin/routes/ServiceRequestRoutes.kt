package com.routes

import com.dtos.requests.serviceRequest.AcceptServiceRequestDto
import com.dtos.requests.serviceRequest.CreateServiceRequestDto
import com.dtos.requests.serviceRequest.UpdateServiceRequestDto
import com.exceptions.AppException
import com.services.ServiceRequestService
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerServiceRequestsRoutes(
    service : ServiceRequestService
) {
    route("/customer_service_request") {
        authenticate("auth_jwt") {
            post("/create") {
                val principal = call.principal<JWTPrincipal>()
                val id = principal?.getClaim("userId", Int::class)
                val role = principal?.getClaim("role" , String::class)

                if (role != "customer") throw AppException.ForbiddenException()

                val request = call.receive<CreateServiceRequestDto>()
                try {
                    val success = service.create(id!! ,request)
                    if (!success) throw AppException.BadRequestException("Service Request Not Created")
                    call.respond(HttpStatusCode.OK , "Service Request Created Successfully")
                } catch (e : Exception) {
                    throw AppException.InternalServerError()
                }
            }
            get {
                val principal = call.principal<JWTPrincipal>()
                val id = principal?.getClaim("userId" , Int::class)
                val role = principal?.getClaim("role" , String::class)

                if (role != "customer") throw AppException.ForbiddenException()
                println("$role , $id")
                try {
                    val responseData = service.getForCustomer(id!!) ?: emptyList()
                    call.respond(HttpStatusCode.OK , responseData)
                }catch (e : Exception) {
                    throw AppException.InternalServerError()
                }
            }
            get("{id}") {
                val requestId = call.parameters["id"]?.toIntOrNull() ?: throw AppException.BadRequestException("Invalid Id")

                val principal = call.principal<JWTPrincipal>()
                val role = principal?.getClaim("role" , String::class)

                if (role != "customer") throw AppException.ForbiddenException()

                try {
                    val data = service.getById(requestId) ?: throw AppException.NotFoundException("Service Request Not Found" , requestId)
                    call.respond(HttpStatusCode.OK , data)
                }catch (e : Exception) {
                    throw AppException.InternalServerError()
                }
            }
        }
    }
}

fun Route.professionalServiceRequestsRoute(
    service : ServiceRequestService
) {
    route("/professional_service_request"){
        authenticate("auth_jwt") {
            get {
                val principal = call.principal<JWTPrincipal>()
                val id = principal?.getClaim("userId" , Int::class)
                val role = principal?.getClaim("role" , String::class)

                if (role != "professional") throw AppException.ForbiddenException()

                try {
                    val response = service.getForProfessional(id!!) ?: emptyList()
                    call.respond(HttpStatusCode.OK , response)
                }catch (e : Exception) {
                    throw AppException.InternalServerError()
                }
            }
            post("/accept") {
                val data = call.receive<AcceptServiceRequestDto>()
                val principal = call.principal<JWTPrincipal>()
                val id = principal?.getClaim("userId" , Int::class)
                val role = principal?.getClaim("role" , String::class)

                if (role != "professional") throw AppException.ForbiddenException()

                try {
                    val success = service.accept( id!! , data.requestId)
                    if (!success) throw AppException.BadRequestException("Service Request Failed")
                    call.respond(HttpStatusCode.OK , "Accepted")
                }catch (e : Exception) {
                    throw AppException.InternalServerError()
                }
            }
            post("/updateStatus") {
                val body = call.receive<UpdateServiceRequestDto>()
                val principal = call.principal<JWTPrincipal>()
                val role = principal?.getClaim("role" , String::class)

                if (role != "professional") throw AppException.ForbiddenException()

                try {
                    val success = service.updateStatus(requestId = body.requestId , newStatus = body.newStatus)
                    if (!success) throw AppException.BadRequestException("Update Failed")
                    call.respond(HttpStatusCode.OK , "Status updated Successfully")
                }catch (e : Exception) {
                    throw AppException.InternalServerError()
                }
            }
            get("{id}") {
                val requestId = call.parameters["id"]?.toIntOrNull() ?: throw AppException.BadRequestException("Invalid Id")

                val principal = call.principal<JWTPrincipal>()
                val role = principal?.getClaim("role" , String::class)

                if (role != "professional") throw AppException.ForbiddenException()

                try {
                    val data = service.getById(requestId) ?: throw AppException.NotFoundException("Service Request Not Found" , requestId)
                    call.respond(HttpStatusCode.OK , data)
                }catch (e : Exception) {
                    throw AppException.InternalServerError()
                }
            }
        }
    }

}