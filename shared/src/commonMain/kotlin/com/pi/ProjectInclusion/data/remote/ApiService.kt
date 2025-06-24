package com.pi.ProjectInclusion.data.remote

import com.pi.ProjectInclusion.data.model.GetLanguageListResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class ApiService(private val client: HttpClient) {

    private val STUDENT_BASE_URL = "https://student-api.auroscholar.org/api/"   // Production

    suspend fun getLanguages(page: String, limit: String): GetLanguageListResponse  = client.get{
        url(STUDENT_BASE_URL)
        parameter("page",page)
        parameter("limit",limit)
    }.body<GetLanguageListResponse>()
}