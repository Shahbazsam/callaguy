package com.routes

import com.dtos.requests.approval.ApproveOrRejectProfessional
import com.exceptions.AppException
import com.services.ApprovalService
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.adminProfessionalsPendingRoute(
    approvalService: ApprovalService
) {
    route("/admin") {
        authenticate("auth_jwt") {
            get("/pending_approval") {

                val principal = call.principal<JWTPrincipal>()
                val role = principal?.getClaim("role" , String::class)

                if (role != "admin") throw AppException.ForbiddenException()
                val result = approvalService.getNotApprovedProfessional()

                call.respond(HttpStatusCode.OK , result)
            }
            post ("/approve"){
                val principal = call.principal<JWTPrincipal>()
                val role = principal?.getClaim("role" , String::class)

                if (role != "admin") throw AppException.ForbiddenException()
                val request = call.receive<ApproveOrRejectProfessional>()

                approvalService.approveProfessionals(request.id)
                call.respond(HttpStatusCode.OK, "Approved Successfully")
            }
            delete ("/reject/{id}"){
                val principal = call.principal<JWTPrincipal>()
                val role = principal?.getClaim("role" , String::class)

                if (role != "admin") throw AppException.ForbiddenException()
                val id = call.parameters["id"]?.toIntOrNull()
                    ?: throw AppException.BadRequestException("Id Not Found")

                approvalService.rejectProfessional(id)
                call.respond(HttpStatusCode.OK , "Professional Rejected Successfully")
            }
        }
    }
}