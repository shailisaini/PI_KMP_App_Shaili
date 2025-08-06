package com.pi.ProjectInclusion.data.remote

import com.pi.ProjectInclusion.data.model.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.GetUserTypeResponse
import com.pi.ProjectInclusion.data.model.SendOTPResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom
import kotlinx.serialization.json.Json

class ApiService(private val client: HttpClient) {

    companion object {
        //    private val STUDENT_BASE_URL = "https://staging-api-pi.projectinclusion.in/api/"   // Production BASE URL
//        const val STUDENT_BASE_URL = "https://student-api.auroscholar.org/api/"                         // Production
        const val STUDENT_BASE_URL = "https://staging-pi-api.projectinclusion.in/api/v2"
    }

    suspend fun getLanguages(): GetLanguageListResponse = client.get {
        url {
            takeFrom(STUDENT_BASE_URL)
            appendPathSegments("language", "get-all") // end points
        }
        headers {
            append(HttpHeaders.Accept, "application/json")
        }
    }.body<GetLanguageListResponse>()


    // it is commenting if we need it later for testing response
    /*suspend fun getUserType(): GetUserTypeResponse {
        val response: HttpResponse = client.get {*/

    suspend fun getUserType(): GetUserTypeResponse = client.get {

            url {
                takeFrom(STUDENT_BASE_URL)
                appendPathSegments("user-type", "get-for-app")
            }
            headers {
                append(HttpHeaders.Accept, "application/json")
            }

        /*val raw = response.bodyAsText()
        println("Raw Response:\n$raw")
        return Json.decodeFromString<GetUserTypeResponse>(raw)*/
    }.body<GetUserTypeResponse>()

    suspend fun getOTPOnCall(mobNo : String): SendOTPResponse = client.post {
        url {
            takeFrom(STUDENT_BASE_URL)
            appendPathSegments("users", "otp-on-call") // → /language/get-all
            parameters.append("mobileNe", mobNo)
        }
        headers {
            append(HttpHeaders.Accept, "application/json")
        }
    }.body<SendOTPResponse>()

    suspend fun getOTPOnWhatsapp(mobNo : String): SendOTPResponse = client.post {
        url {
            takeFrom(STUDENT_BASE_URL)
            appendPathSegments("users", "send-otp-whatsapp") // → /language/get-all
            parameters.append("mobileNo", mobNo)
        }
        headers {
            append(HttpHeaders.Accept, "application/json")
        }
    }.body<SendOTPResponse>()
}
