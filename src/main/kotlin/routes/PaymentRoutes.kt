package com.routes

import com.dtos.requests.payment.InitialPaymentRequestDto
import com.exceptions.AppException
import com.services.PaymentService
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.paymentRoutes(
    service : PaymentService
) {

    route("/payment") {
        authenticate("auth_jwt") {
            post("/make_payment"){
                val principal = call.principal<JWTPrincipal>()
                val role = principal?.getClaim("role", String::class)
                if (role != "customer") throw AppException.ForbiddenException()

                val request = call.receive<InitialPaymentRequestDto>()
                try {
                   val success = service.payment(request)

                    if (!success) throw AppException.BadRequestException("Payment Failed")

                    call.respond(HttpStatusCode.OK , "Payment Successful")
                }catch (e : Exception) {
                    throw AppException.InternalServerError()
                }
            }
            get("/payment_information{id}") {
                val requestId = call.parameters["id"]?.toIntOrNull() ?: throw AppException.BadRequestException("Id Null")

                val principal = call.principal<JWTPrincipal>()
                val role = principal?.getClaim("role" , String::class)

                if (role != "customer") throw AppException.ForbiddenException()

                try {
                    val response = service.paymentInformation(requestId) ?: throw AppException.NotFoundException("Payment Not Found" , requestId)
                    call.respond(HttpStatusCode.OK , response)
                }catch (e : Exception) {
                    throw AppException.InternalServerError()
                }
            }
            put("/{id}/status") {
                val paymentId = call.parameters["id"]?.toIntOrNull() ?: throw AppException.ForbiddenException()

                val principal = call.principal<JWTPrincipal>()
                val role = principal?.getClaim("role" , String::class)

                if (role != "customer") throw AppException.ForbiddenException()
                try {
                    val success = service.updatePaymentStatus(paymentId)
                    if (!success) throw AppException.BadRequestException("Payment Update failed")
                    call.respond(HttpStatusCode.OK , "Payment Status Updated Successfully")
                }catch (e : Exception) {
                    throw AppException.InternalServerError()
                }
            }
        }
    }
}