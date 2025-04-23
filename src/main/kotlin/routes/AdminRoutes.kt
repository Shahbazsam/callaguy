package com.routes

import com.dtos.requests.auth.AdminRegisterRequestDto
import com.dtos.requests.auth.LoginRequest
import com.dtos.response.auth.AuthResponse
import com.exceptions.AppException
import com.services.AdminService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.adminAuthRoutes(
    service : AdminService
) {
    route("/admin_auth") {
        post("/register") {
            val request = call.receive<AdminRegisterRequestDto>()
            val success = service.registerAdmin(request)
            if (success) {
                call.respond(
                    HttpStatusCode.OK , "Registration Successful"
                )
            } else {
                throw AppException.InternalServerError()
            }
        }
        post("/login") {
            val credentials = call.receive<LoginRequest>()
            try {
                val token = service.Authenticate(credentials)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = AuthResponse(
                        token = token
                    )
                )
            }catch (e : AppException.UnauthorizedException) {
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = mapOf("code" to e.code, "message" to e.message)
                )
            }catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("code" to "INTERNAL_ERROR", "message" to "An unexpected error occurred"))
            }
        }
    }
}