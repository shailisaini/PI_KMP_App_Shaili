package com.pi.ProjectInclusion.domain.repository

import com.pi.ProjectInclusion.data.model.authenticationModel.response.CreateRegisterPasswordResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ForgetPasswordResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetLanguageListResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.GetUserTypeResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.LoginApiResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.SendOTPResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.ValidateUserResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.response.CreateFirstStepProfileResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.ForgetPasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.response.VerifyOtpResponse
import com.pi.ProjectInclusion.data.model.authenticationModel.request.CreatePasswordRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.FirstStepProfileRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginRequest
import com.pi.ProjectInclusion.data.model.authenticationModel.request.LoginWithOtpRequest

interface AuthenticationRepository {
    suspend fun getLanguage(): GetLanguageListResponse
    suspend fun getUserType(): GetUserTypeResponse
    suspend fun getOTPOnCall(mobNo: String): SendOTPResponse
    suspend fun getUserLoginPasswordRepo(loginRequest: LoginRequest): LoginApiResponse
    suspend fun getOTPOnWhatsapp(mobNo: String): SendOTPResponse
    suspend fun getValidate(userName: String, userTypeId: String): ValidateUserResponse
    suspend fun getVerifyOtpRepo(userName: String, userTypeId: String): VerifyOtpResponse
    suspend fun getLoginWithOTPRepo(request: LoginWithOtpRequest): LoginApiResponse
    suspend fun getUserProfileRepo(userName: String): LoginApiResponse
    suspend fun forgetPasswordRepo(
        passwordRequest: ForgetPasswordRequest,
        strToken: String,
    ): ForgetPasswordResponse

    suspend fun createRegisterPasswordRepo(
        passwordRequest: CreatePasswordRequest,
        strToken: String,
    ): CreateRegisterPasswordResponse

    suspend fun createFirstStepProfileRepo(
        firstStepProfileRequest: FirstStepProfileRequest,
        strToken: String,
        profilePic: ByteArray? = null,
        fileName: String? = null
    ): CreateFirstStepProfileResponse
}