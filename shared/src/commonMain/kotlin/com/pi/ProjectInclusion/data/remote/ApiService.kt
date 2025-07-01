package com.pi.ProjectInclusion.data.remote

import com.pi.ProjectInclusion.data.model.GetLanguageListResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom

class ApiService(private val client: HttpClient) {

    private val STUDENT_BASE_URL = "https://student-api.auroscholar.org/api/"   // Production BASE URL

    val STUDENT_END_POINT = STUDENT_BASE_URL + "v1/student/"

    suspend fun getLanguages(page: String, limit: String): GetLanguageListResponse = client.get {
        url {
            takeFrom(STUDENT_END_POINT)
            appendPathSegments("admin", "languages")
            parameters.append("page", page)
            parameters.append("limit", limit)
        }
        headers {
            append(HttpHeaders.Accept, "application/json")
        }
    }.body<GetLanguageListResponse>()
  /*  val raw = response.bodyAsText()
    println("Raw Response:\n$raw")

    if (!response.status.isSuccess()) {
        throw Exception("HTTP ${response.status.value}: $raw")
    }

    return Json {
        ignoreUnknownKeys = true
    }.decodeFromString(GetLanguageListResponse.serializer(), raw)
}*/
}
