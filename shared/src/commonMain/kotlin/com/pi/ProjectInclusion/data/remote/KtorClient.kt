package com.pi.ProjectInclusion.data.remote

import com.example.kmptemplate.logger.LoggerProvider
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.http.contentType
import io.ktor.http.headers

import io.ktor.client.request.delete
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.path

object KtorClient {

    private val timeOut :Long = 3000

    val client = HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }

            install(HttpTimeout) {
                socketTimeoutMillis = timeOut
                connectTimeoutMillis = timeOut
                requestTimeoutMillis = timeOut
            }

        install(Logging){
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    LoggerProvider.logger.d(message)
                }

            }
        }
    }
}