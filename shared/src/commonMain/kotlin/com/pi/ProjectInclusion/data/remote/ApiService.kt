package com.pi.ProjectInclusion.data.remote

import com.pi.ProjectInclusion.data.model.authenticationModel.response.CertificateListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CreateFirstStepProfileResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CertificateRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CreateRegisterPasswordResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ForgetPasswordResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetUserTypeResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.LoginApiResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SendOTPResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ValidateUserResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ForgetPasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.VerifyOtpResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CreatePasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.FirstStepProfileRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginWithOtpRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ProfessionalProfileRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.BlockListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.DistrictListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ProfessionListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.QualificationListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ReasonListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SchoolByUdiseCodeResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SchoolListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SpecializationListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.StateListResponse
import com.pi.ProjectInclusion.data.model.profileModel.ViewProfileResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.takeFrom

class ApiService(private val client: HttpClient) {

    companion object {
        //    private val STUDENT_BASE_URL = "https://staging-api-pi.projectinclusion.in/api/"   // Production BASE URL
//        const val STUDENT_BASE_URL = "https://student-api.auroscholar.org/api/"                         // Production
        const val STUDENT_BASE_URL = "https://staging-pi-api.projectinclusion.in/api/v2"
        const val CERTIFICATE_BASE_URL = "https://lmsapi.projectinclusion.in/api"
        const val BASIC_LIVE_BASE_URL = "https://api-pi.projectinclusion.in"
        const val SCHOOL_LIVE_BASE_URL = "https://api-school.projectinclusion.in"
        const val appendUser = "users"
        const val appendCertificate = "Certificate"
        const val appendLive = "api"
        const val appendReason = "reason"
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

    suspend fun getLMSUserCertificate(
        certificateRequest: CertificateRequest,
        strToken: String,
    ): CertificateListResponse =
        client.post {

            url {
                takeFrom(CERTIFICATE_BASE_URL)
                appendPathSegments(appendCertificate, "GetLMSUserCertificate")
            }
            headers {
                append(HttpHeaders.Accept, "application/json")
//                append(HttpHeaders.Authorization, "Bearer $strToken")
            }
            contentType(ContentType.Application.Json)
            setBody(certificateRequest)
        }.body<CertificateListResponse>()

    // user Profile
    suspend fun getViewUserProfile(username: String): ViewProfileResponse =
        client.get {
            url {
                takeFrom(STUDENT_BASE_URL)
                appendPathSegments(appendUser, "get-user-by-username", username)
            }
            headers {
                append(HttpHeaders.Accept, "application/json")
            }
        }.body<ViewProfileResponse>()

    suspend fun createFirstStepProfile(
        firstStepProfileRequest: FirstStepProfileRequest,
        strToken: String,
        profilePic: ByteArray? = null,
        fileName: String? = null,
    ): CreateFirstStepProfileResponse =
        client.patch {

            url {
                takeFrom(STUDENT_BASE_URL)
                appendPathSegments(appendUser, "update-basic-profile")
            }
            headers {
                append(HttpHeaders.Accept, "application/json")
                append(HttpHeaders.Authorization, strToken)
            }
            contentType(ContentType.Application.Json)
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("firstname", firstStepProfileRequest.firstname.toString())
                        append("middlename", firstStepProfileRequest.email.toString())
                        append("lastname", firstStepProfileRequest.mobile.toString())
                        append("gender", firstStepProfileRequest.mobile.toString())
                        append("mobile", firstStepProfileRequest.mobile.toString())
                        append("whatsapp", firstStepProfileRequest.mobile.toString())
                        append("dob", firstStepProfileRequest.mobile.toString())
                        append("email", firstStepProfileRequest.mobile.toString())
                        profilePic?.let { bytes ->
                            append(
                                key = "profilepic", //params Name
                                value = bytes,
                                headers = Headers.build {
                                    append(
                                        HttpHeaders.ContentType,
                                        ContentType.Image.PNG.toString()
                                    )
                                    append(
                                        HttpHeaders.ContentDisposition,
                                        "form-data; name=\"profilepic\"; filename=\"${fileName ?: "profile.jpg"}\""
                                    )
                                }
                            )
                        }
                    }
                ))
        }.body<CreateFirstStepProfileResponse>()

    suspend fun getAllStateList(): List<StateListResponse> =
        client.get {
            url {
                takeFrom(BASIC_LIVE_BASE_URL)
                appendPathSegments(appendLive, "State")
            }
            headers {
                append(HttpHeaders.Accept, "application/json")
            }
        }.body<List<StateListResponse>>()

    suspend fun getAllDistrictByStateId(stateId: Int): List<DistrictListResponse> =
        client.get {
            url {
                takeFrom(BASIC_LIVE_BASE_URL)
                appendPathSegments(appendLive, "District/GetDistrictByState/${stateId}")
            }
            headers {
                append(HttpHeaders.Accept, "application/json")
            }
        }.body<List<DistrictListResponse>>()

    suspend fun getAllBlockByDistrictId(districtId: Int): List<BlockListResponse> =
        client.get {
            url {
                takeFrom(BASIC_LIVE_BASE_URL)
                appendPathSegments(appendLive, "Block/GetBlockByDistrict/${districtId}")
            }
            headers {
                append(HttpHeaders.Accept, "application/json")
            }
        }.body<List<BlockListResponse>>()

    suspend fun getAllSchoolsByBlockId(blockId: Int): SchoolListResponse =
        client.get {
            url {
                takeFrom(SCHOOL_LIVE_BASE_URL)
                appendPathSegments(appendLive, "SchoolMaster/GetSchoolsByBlockId/${blockId}")
            }
            headers {
                append(HttpHeaders.Accept, "application/json")
            }
        }.body<SchoolListResponse>()

    suspend fun getAllDetailsByUdiseId(udiseCode: String): SchoolByUdiseCodeResponse =
        client.get {
            url {
                takeFrom(SCHOOL_LIVE_BASE_URL)
                appendPathSegments(appendLive, "SchoolMaster/GetSchoolByUdiseCode")
                parameters.append("udiseCode", udiseCode)
            }
            headers {
                append(HttpHeaders.Accept, "application/json")
            }
        }.body<SchoolByUdiseCodeResponse>()

    suspend fun createProfessionalProfile(
        professionalProfileRequest: ProfessionalProfileRequest,
        strToken: String,
    ): CreateFirstStepProfileResponse =
        client.post {
            url {
                takeFrom(STUDENT_BASE_URL)
                appendPathSegments(appendUser, "update-professional-profile")
            }
            headers {
                append(HttpHeaders.Accept, "application/json")
                append(HttpHeaders.Authorization, "Bearer $strToken")
            }
            contentType(ContentType.Application.Json)
            setBody(professionalProfileRequest)
        }.body<CreateFirstStepProfileResponse>()

    suspend fun getAllProfession(): List<ProfessionListResponse> =
        client.get {
            url {
                takeFrom(BASIC_LIVE_BASE_URL)
                appendPathSegments(appendLive, "Profession/GetProfession")
            }
            headers {
                append(HttpHeaders.Accept, "application/json")
            }
        }.body<List<ProfessionListResponse>>()

    suspend fun getAllQualification(profession: Int): List<QualificationListResponse> =
        client.get {
            url {
                takeFrom(BASIC_LIVE_BASE_URL)
                appendPathSegments(appendLive, "Profession/GetQualification/${profession}")
            }
            headers {
                append(HttpHeaders.Accept, "application/json")
            }
        }.body<List<QualificationListResponse>>()

    suspend fun getAllSpecialization(
        profession: Int,
        qualification: Int,
    ): List<SpecializationListResponse> =
        client.get {
            url {
                takeFrom(BASIC_LIVE_BASE_URL)
                appendPathSegments(
                    appendLive,
                    "Profession/GetSpecialization/${profession}/${qualification}"
                )
            }
            headers {
                append(HttpHeaders.Accept, "application/json")
            }
        }.body<List<SpecializationListResponse>>()

    suspend fun getAllReason(): ReasonListResponse =
        client.get {
            url {
                takeFrom(STUDENT_BASE_URL)
                appendPathSegments(appendReason, "get-all")
            }
            headers {
                append(HttpHeaders.Accept, "application/json")
            }
        }.body<ReasonListResponse>()
}
