package com


import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Application.configureSwagger() {
    routing {
        swaggerUI(path = SWAGGER_PATH , swaggerFile = SWAGGER_FILE_PATH)
    }

}

private const val SWAGGER_PATH = "openapi"
private const val SWAGGER_FILE_PATH = "openapi/documentation.yaml"