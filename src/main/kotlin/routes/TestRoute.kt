package com.routes

import com.utils.Constants
import com.utils.save
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Route.testImage() {
    route("/test"){
        authenticate("auth_jwt") {
            post("/test_image"){
                val multipart = call.receiveMultipart()
                var fileName : String? = null
                var imageStringUrl : String? = null

                try {
                    multipart.forEachPart { part ->
                        when(part) {
                            is PartData.FileItem -> {
                                if (part.name == "image"){
                                    fileName = part.save(Constants.TESTING_PATH)
                                    imageStringUrl = "${Constants.BASE_URL}${Constants.EXTERNAL_TESTING_PATH}/$fileName"
                                }
                            }
                            else -> Unit
                        }
                    }
                }catch (e : Exception){
                    File("${Constants.TESTING_PATH}/$fileName").delete()
                    call.respond(HttpStatusCode.InternalServerError , "Error")
                }
                call.respond(HttpStatusCode.OK, mapOf("imageUrl" to  imageStringUrl))
            }
        }
    }
}