package com.routes

import com.dtos.response.ProfilePicture
import com.exceptions.AppException
import com.utils.Constants
import com.utils.save
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import java.io.File

fun Route.profilePicture() {
    route("/profile_picture") {
        authenticate("auth_jwt") {
            post {
                val principal = call.principal<JWTPrincipal>()
                val role = principal?.getClaim("role" , String::class)
                println(role)
                if (role != "customer") throw AppException.BadRequestException("Wrong individual")

                var fileName : String? = null
                var imageUrl : String? = null

                val multipart = call.receiveMultipart()
                try {
                    multipart.forEachPart { data ->
                        if (data is PartData.FileItem) {
                            fileName = data.save(Constants.STATIC_CUSTOMER_PROFILE_PICTURE_PATH)
                            imageUrl = "${Constants.BASE_URL}${Constants.EXTERNAL_CUSTOMER_PROFILE_PICTURE_PATH}/$fileName"
                        }
                    }
                    val response = ProfilePicture(
                        imageUrl = imageUrl
                    )
                    println(" role = $role")
                    println(response.imageUrl)
                    println()
                    call.respond(HttpStatusCode.OK , response )
                } catch (e : Exception) {
                    File("${Constants.STATIC_CUSTOMER_PROFILE_PICTURE_PATH}/$fileName").delete()
                }
            }
        }
    }
}