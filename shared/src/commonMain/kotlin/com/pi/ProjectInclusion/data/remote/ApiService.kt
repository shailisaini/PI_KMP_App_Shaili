package com.pi.ProjectInclusion.data.remote

import com.pi.ProjectInclusion.data.model.authenticationModel.response.CreateRegisterPasswordResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ForgetPasswordResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetUserTypeResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.LoginApiResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SendOTPResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ValidateUserResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ForgetPasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.VerifyOtpResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CreatePasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginWithOtpRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.patch
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

    suspend fun getValidateUser(userName: String, userTypeId: String): ValidateUserResponse =
        client.get {
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

    suspend fun getOTPOnCall(mobNo: String): SendOTPResponse = client.post {
        url {
            takeFrom(STUDENT_BASE_URL)
            appendPathSegments(appendUser, "otp-on-call")
            parameters.append("mobileNo", mobNo)
        }
        headers {
            append(HttpHeaders.Accept, "application/json")
        }
    }.body<SendOTPResponse>()

    suspend fun getOTPOnWhatsapp(mobNo: String): SendOTPResponse = client.post {
        url {
            takeFrom(STUDENT_BASE_URL)
            appendPathSegments(appendUser, "send-otp-whatsapp") // → /language/get-all
            parameters.append("mobileNe", mobNo)
        }
        headers {
            append(HttpHeaders.Accept, "application/json")
        }
    }.body<SendOTPResponse>()

    // registration, forgot password & recover user
    suspend fun getVerifyOTP(mobNo: String, otpValue: String): VerifyOtpResponse = client.post {
        url {
            takeFrom(STUDENT_BASE_URL)
            appendPathSegments(appendUser, "verify-otp") // → /language/get-all
            parameters.append("mobileNo", mobNo)
            parameters.append("otp", otpValue)
        }
        headers {
            append(HttpHeaders.Accept, "application/json")
        }
    }.body<VerifyOtpResponse>()

    suspend fun getLoginWithOTP(request: LoginWithOtpRequest): LoginApiResponse = client.post {
        url {
            takeFrom(STUDENT_BASE_URL)
            appendPathSegments(appendUser, "login-with-otp")
        }
        headers {
            append(HttpHeaders.Accept, "application/json")
        }
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body<LoginApiResponse>()

    suspend fun forgetPassword(
        passwordRequest: ForgetPasswordRequest,
        strToken: String,
    ): ForgetPasswordResponse =
        client.patch {

            url {
                takeFrom(STUDENT_BASE_URL)
                appendPathSegments(appendUser, "forget-password")
            }
            headers {
                append(HttpHeaders.Accept, "application/json")
                append(HttpHeaders.Authorization, "Bearer $strToken")
            }
            contentType(ContentType.Application.Json)
            setBody(passwordRequest)
        }.body<ForgetPasswordResponse>()

    suspend fun createRegisterPassword(
        changeRequest: CreatePasswordRequest,
        strToken: String,
    ): CreateRegisterPasswordResponse =
        client.post {

            url {
                takeFrom(STUDENT_BASE_URL)
                appendPathSegments(appendUser, "register-user")
            }
            headers {
                append(HttpHeaders.Accept, "application/json")
                append(HttpHeaders.Authorization, "Bearer $strToken")
            }
            contentType(ContentType.Application.Json)
            setBody(changeRequest)
        }.body<CreateRegisterPasswordResponse>()

    // user Profile
    suspend fun getViewUserProfile(username : String): LoginApiResponse =
        client.get {
            url {
                takeFrom(STUDENT_BASE_URL)
                appendPathSegments(appendUser, "get-user-by-username")
                parameters.append("username", username)
            }
            headers {
                append(HttpHeaders.Accept, "application/json")
            }
        }.body<LoginApiResponse>()
}
