package com

import com.utils.LocalDateSerializer
import com.utils.LocalDateTimeSerializer
import com.utils.LocalTimeSerializer
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(
            Json {
                serializersModule = SerializersModule {
                    contextual(LocalDate::class , LocalDateSerializer)
                    contextual(LocalTime::class , LocalTimeSerializer)
                    contextual(LocalDateTime::class , LocalDateTimeSerializer)
                }
                ignoreUnknownKeys = true
            }
        )
    }
}
