package com.pi.ProjectInclusion.data.remote

import com.pi.ProjectInclusion.data.model.authenticationModel.Response.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.GetUserTypeResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.LoginApiResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.SendOTPResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.Response.ValidateUserResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.takeFrom

class ApiService(private val client: HttpClient) {

    companion object {
        //    private val STUDENT_BASE_URL = "https://staging-api-pi.projectinclusion.in/api/"   // Production BASE URL
//        const val STUDENT_BASE_URL = "https://student-api.auroscholar.org/api/"                         // Production
        const val STUDENT_BASE_URL = "https://staging-pi-api.projectinclusion.in/api/v2"
        const val appendUser = "users"
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

    // Login API's

    suspend fun getValidateUser(userName : String,userTypeId : String): ValidateUserResponse = client.get {
        url {
            takeFrom(STUDENT_BASE_URL)
            appendPathSegments(appendUser, "validate-user") // api end points
            parameters.append("username", userName)
            parameters.append("userTypeId", userTypeId)
        }
        headers {
            append(HttpHeaders.Accept, "application/json")
        }
    }.body<ValidateUserResponse>()

    suspend fun getUserLoginWithPassword(request: LoginRequest): LoginApiResponse = client.post {
        url {
            takeFrom(STUDENT_BASE_URL)
            appendPathSegments(appendUser, "login") // api end points
        }
        headers {
            append(HttpHeaders.Accept, "application/json")
        }
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body<LoginApiResponse>()

    suspend fun getOTPOnCall(mobNo : String): SendOTPResponse = client.post {
        url {
            takeFrom(STUDENT_BASE_URL)
            appendPathSegments(appendUser, "otp-on-call")
            parameters.append("mobileNo", mobNo)
        }
        headers {
            append(HttpHeaders.Accept, "application/json")
        }
    }.body<SendOTPResponse>()

    suspend fun getOTPOnWhatsapp(mobNo : String): SendOTPResponse = client.post {
        url {
            takeFrom(STUDENT_BASE_URL)
            appendPathSegments(appendUser, "send-otp-whatsapp") // → /language/get-all
            parameters.append("mobileNe", mobNo)
        }
        headers {
            append(HttpHeaders.Accept, "application/json")
        }
    }.body<SendOTPResponse>()
}
