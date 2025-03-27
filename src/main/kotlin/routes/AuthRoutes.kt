package com.routes

import com.dtos.requests.auth.LoginRequest
import com.dtos.requests.auth.RegisterRequest
import com.dtos.response.auth.AuthResponse
import com.exceptions.AppException
import com.services.AuthService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.authRoutes(
    authService: AuthService
) {
    route("/auth") {
        post("/register") {
            val request = call.receive<RegisterRequest>()
            val success = authService.registerUser(request)
            if (success) {
                call.respond(
                   status =  HttpStatusCode.OK ,
                   message = "Registration Successful"
                    )
            } else {
                throw AppException.InternalServerError()
            }
        }
        post("/login") {
            val credentials = call.receive<LoginRequest>()
            try {
                val token = authService.authenticate(credentials)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = AuthResponse(
                        token
                    )
                )
            } catch (e : AppException.UnauthorizedException) {
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