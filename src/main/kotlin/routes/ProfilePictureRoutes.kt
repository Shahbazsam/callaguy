package com.routes

import com.exceptions.AppException
import com.services.CustomerAuthService
import com.services.ProfilePictureService
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerProfilePicture(
    service : ProfilePictureService,
    customerService : CustomerAuthService
) {
    route("/customer_profile") {
        authenticate("auth_jwt") {
            post("/picture") {
                val principal = call.principal<JWTPrincipal>()
                val id = principal?.getClaim("userId" , Int::class) ?: throw AppException.UnauthorizedException("Not Authorized")
                val role = principal.getClaim("role" , String::class) ?: throw AppException.UnauthorizedException("Not Authorized")
                if (role != "customer") throw AppException.UnauthorizedException("Not Authorized")

                var uploadedFile : PartData.FileItem? = null

                val multipart = call.receiveMultipart()
                try {
                    multipart.forEachPart { data ->
                        if (data is PartData.FileItem) uploadedFile = data
                    }
                    if (uploadedFile == null) throw AppException.BadRequestException("No Image Found")
                    val success = service.updateCustomerProfilePicture(customerId = id , file = uploadedFile )
                    if (success){
                        call.respond(HttpStatusCode.OK ,  "Updated Successfully")
                    } else {
                        throw AppException.InternalServerError()
                    }
                } catch (e : Exception) {
                    throw AppException.InternalServerError()
                }

            }
            get("/profile_info") {
                val principal = call.principal<JWTPrincipal>()
                val id = principal?.getClaim("userId" , Int::class) ?: throw AppException.UnauthorizedException("Not Authorized")
                val role = principal.getClaim("role" , String::class) ?: throw AppException.UnauthorizedException("Not Authorized")
                if (role != "customer") throw AppException.UnauthorizedException("Not Authorized")

                try {
                    val response = customerService.getProfileInformation(id) ?: throw AppException.NotFoundException("Profile Not Found" , id)
                    call.respond(HttpStatusCode.OK ,  response)
                }catch (e : Exception) {
                    throw AppException.InternalServerError()
                }
            }
        }
    }

}

fun Route.professionalProfilePicture(
    service : ProfilePictureService
) {
    route("/professional_profile_picture") {
        authenticate("auth_jwt") {
            post {
                val principal = call.principal<JWTPrincipal>()
                val id = principal?.getClaim("userId" , Int::class) ?: throw AppException.UnauthorizedException("Not Authorized")
                val role = principal.getClaim("role" , String::class) ?: throw AppException.UnauthorizedException("Not Authorized")
                if (role != "professional") throw AppException.UnauthorizedException("Not Authorized")

                var uploadedFile : PartData.FileItem? = null

                val multipart = call.receiveMultipart()
                try {
                    multipart.forEachPart { data ->
                        if (data is PartData.FileItem) uploadedFile = data
                    }
                    if (uploadedFile == null) throw AppException.BadRequestException("No Image Found")
                    val success = service.updateProfessionalProfilePicture(professionalId = id , file = uploadedFile )
                    if (success){
                        call.respond(HttpStatusCode.OK ,  "Updated Successfully")
                    } else {
                        throw AppException.InternalServerError()
                    }
                } catch (e : Exception) {
                    throw AppException.InternalServerError()
                }
            }
        }
    }
}






/*var fileName : String? = null
var imageUrl : String? = null
fileName = data.save(Constants.STATIC_CUSTOMER_PROFILE_PICTURE_PATH)
imageUrl = "${Constants.BASE_URL}${Constants.EXTERNAL_CUSTOMER_PROFILE_PICTURE_PATH}/$fileName"*/