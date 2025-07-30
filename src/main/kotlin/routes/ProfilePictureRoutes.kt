package com.routes

import com.exceptions.AppException
import com.services.CustomerAuthService
import com.services.ProfessionalAuthService
import com.services.ProfilePictureService
import com.utils.Constants
import com.utils.save
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Route.customerProfilePicture(
    service : ProfilePictureService,
    customerService : CustomerAuthService
) {
    route("/customer_profile") {
        authenticate("auth_jwt") {
            post {
                var fileName: String? = null
                var imageUrl: String? = null

                try {
                    val principal = call.principal<JWTPrincipal>()
                    val id = principal?.getClaim("userId", Int::class)
                        ?: throw AppException.UnauthorizedException("Not Authorized")
                    val role = principal.getClaim("role", String::class)
                        ?: throw AppException.UnauthorizedException("Not Authorized")
                    if (role != "customer") {
                        throw AppException.UnauthorizedException("Not Authorized")
                    }

                    val multipart = call.receiveMultipart()

                    multipart.forEachPart { part ->
                        when (part) {
                            is PartData.FileItem -> {
                                if (part.name == "image") {
                                    fileName = part.save(Constants.STATIC_CUSTOMER_PROFILE_PICTURE_PATH)
                                    imageUrl = "${Constants.BASE_URL}${Constants.EXTERNAL_CUSTOMER_PROFILE_PICTURE_PATH}/$fileName"
                                }
                            }
                            else -> Unit
                        }
                        part.dispose()
                    }
                    if (imageUrl == null || fileName == null) {
                        throw AppException.BadRequestException("No image found in request")
                    }
                    service.updateCustomerProfilePicture (id, imageUrl)
                    call.respond(HttpStatusCode.OK, mapOf("imageUrl" to imageUrl))
                } catch (e: AppException) {
                    e.printStackTrace()
                    fileName?.let {
                        val file = File("${Constants.STATIC_CUSTOMER_PROFILE_PICTURE_PATH}/$it")
                        if (file.exists()) file.delete()
                    }
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Something went wrong")
                } catch (e: Exception) {
                    e.printStackTrace()
                    fileName?.let {
                        val file = File("${Constants.STATIC_CUSTOMER_PROFILE_PICTURE_PATH}/$it")
                        if (file.exists()) file.delete()
                    }
                    call.respond(HttpStatusCode.InternalServerError, "Something went wrong")
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
    service : ProfilePictureService,
    professionalAuthService: ProfessionalAuthService
) {
    route("/professional_profile_picture") {
        authenticate("auth_jwt") {
            post {
                var fileName: String? = null
                var imageUrl: String? = null

                try {
                    val principal = call.principal<JWTPrincipal>()
                    val id = principal?.getClaim("userId", Int::class)
                        ?: throw AppException.UnauthorizedException("Not Authorized")
                    val role = principal.getClaim("role", String::class)
                        ?: throw AppException.UnauthorizedException("Not Authorized")
                    if (role != "professional") {
                        throw AppException.UnauthorizedException("Not Authorized")
                    }

                    val multipart = call.receiveMultipart()

                    multipart.forEachPart { part ->
                        when (part) {
                            is PartData.FileItem -> {
                                if (part.name == "image") {
                                    fileName = part.save(Constants.STATIC_PROFESSIONAL_PROFILE_PICTURE_PATH)
                                    imageUrl = "${Constants.BASE_URL}${Constants.EXTERNAL_PROFESSIONAL_PROFILE_PICTURE_PATH}/$fileName"
                                }
                            }
                            else -> Unit
                        }
                        part.dispose()
                    }
                    if (imageUrl == null || fileName == null) {
                        throw AppException.BadRequestException("No image found in request")
                    }
                    service.updateProfessionalProfilePicture(id, imageUrl)
                    call.respond(HttpStatusCode.OK, mapOf("imageUrl" to imageUrl))
                } catch (e: AppException) {
                    e.printStackTrace()
                    fileName?.let {
                        val file = File("${Constants.STATIC_PROFESSIONAL_PROFILE_PICTURE_PATH}/$it")
                        if (file.exists()) file.delete()
                    }
                    call.respond(HttpStatusCode.InternalServerError, e.message ?: "Something went wrong")
                } catch (e: Exception) {
                    e.printStackTrace()
                    fileName?.let {
                        val file = File("${Constants.STATIC_PROFESSIONAL_PROFILE_PICTURE_PATH}/$it")
                        if (file.exists()) file.delete()
                    }
                    call.respond(HttpStatusCode.InternalServerError, "Something went wrong")
                }
            }
            get("/profile_info") {
                val principal = call.principal<JWTPrincipal>()
                val id = principal?.getClaim("userId" , Int::class) ?: throw AppException.UnauthorizedException("Not Authorized")
                val role = principal.getClaim("role" , String::class) ?: throw AppException.UnauthorizedException("Not Authorized")
                if (role != "professional") throw AppException.UnauthorizedException("Not Authorized")

                try {
                    val response = professionalAuthService.profileInfo(id)
                    call.respond(HttpStatusCode.OK ,  response)
                }catch (e : Exception) {
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